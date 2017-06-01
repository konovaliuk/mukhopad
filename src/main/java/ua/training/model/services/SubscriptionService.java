package ua.training.model.services;

import ua.training.model.dao.*;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;

import javax.servlet.http.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class SubscriptionService {
    private static final SubscriptionDao DAO = MysqlDaoFactory.getInstance().getSubscriptionDao();
    private static SubscriptionService instance;

    private SubscriptionService() {}

    public static synchronized SubscriptionService getInstance() {
        if (instance == null) {
            instance = new SubscriptionService();
        }
        return instance;
    }

    private static final String USERNAME = "user";
    private static final String EDITION = "edition";
    private static final String PLAN = "plan";

    public void subscribeUser(HttpServletRequest request, HttpServletResponse response) {
        User user = getUser(request);
        PeriodicalEdition edition = getEdition(request);
        SubscriptionPlan plan = getPlan(request);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Transaction transaction = doTransaction(user, currentTime, edition, plan);

        Timestamp expirationDate = calculateExpirationDate(currentTime, plan);
        DAO.insert(new Subscription(user, edition, transaction, expirationDate));
    }

    Transaction doTransaction(User user, Timestamp currentTime, PeriodicalEdition edition, SubscriptionPlan plan) {
        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        Transaction transaction = new Transaction(0, user, currentTime, totalPrice);

        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        transactionDao.insert(transaction);
        return transaction;
    }

    Timestamp calculateExpirationDate(Timestamp dateOfSubscription, SubscriptionPlan plan) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfSubscription);
        cal.add(Calendar.MONTH, plan.getAmountOfMonths());
        return new Timestamp(cal.getTime().getTime());
    }

    BigDecimal calculateTotalPrice(PeriodicalEdition edition, SubscriptionPlan plan) {
        int editionPrice = edition.getEditionPrice().movePointRight(2).intValue();
        int totalPrice = (int) (editionPrice * plan.getAmountOfMonths() * plan.getRate());
        return BigDecimal.valueOf(totalPrice).movePointLeft(2);
    }

    private User getUser(HttpServletRequest request) {
        UserDao userDao = MysqlDaoFactory.getInstance().getUserDao();
        String username = (String) request.getSession().getAttribute(USERNAME);
        return userDao.findByUsername(username);
    }

    private PeriodicalEdition getEdition(HttpServletRequest request) {
        int editionId = (int) request.getSession().getAttribute(EDITION);
        PeriodicalDao periodicalDao = MysqlDaoFactory.getInstance().getPeriodicalDao();
        return periodicalDao.findById(editionId);
    }

    private SubscriptionPlan getPlan(HttpServletRequest request) {
        String planName = (String)request.getSession().getAttribute(PLAN);
        return SubscriptionPlan.valueOf(planName);
    }

}
