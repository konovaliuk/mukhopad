package ua.training.model.services;

import org.apache.logging.log4j.Logger;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;
import ua.training.util.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class SubscriptionService {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SubscriptionService.class);
    private static final SubscriptionDao DAO = MysqlDaoFactory.getInstance().getSubscriptionDao();
    private static SubscriptionService instance;

    private SubscriptionService() {}

    public static synchronized SubscriptionService getInstance() {
        if (instance == null) {
            instance = new SubscriptionService();
        }
        return instance;
    }

    private static final String SESSION_USER = "user";
    private static final String EDITION = "editionId";
    private static final String PLAN = "plan";

    public boolean subscribeUser(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        PeriodicalEdition edition = getEdition(request);
        SubscriptionPlan plan = getPlan(request);
        LOGGER.info("" + user + edition + plan);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Transaction transaction = doTransaction(user, currentTime, edition, plan);

        Timestamp expirationDate = calculateExpirationDate(currentTime, plan);
        return DAO.insert(new Subscription(user, edition, transaction, expirationDate));
    }

    public String proceedToCheckOut(HttpServletRequest request, HttpServletResponse response) {
        SubscriptionPlan plan = getPlan(request);
        request.setAttribute("plan", plan);

        PeriodicalEdition edition =  getEdition(request);
        request.setAttribute("edition", edition);

        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        request.setAttribute("totalPrice", totalPrice);

        return Config.getInstance().getProperty(Config.CHECKOUT);
    }

    private Transaction doTransaction(User user, Timestamp currentTime, PeriodicalEdition edition, SubscriptionPlan plan) {
        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        Transaction transaction = new Transaction(transactionDao.tableSize() + 1, user, currentTime, totalPrice);
        transactionDao.insert(transaction);
        return transaction;
    }

    private Timestamp calculateExpirationDate(Timestamp dateOfSubscription, SubscriptionPlan plan) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateOfSubscription);
        cal.add(Calendar.MONTH, plan.getAmountOfMonths());
        return new Timestamp(cal.getTime().getTime());
    }

    private BigDecimal calculateTotalPrice(PeriodicalEdition edition, SubscriptionPlan plan) {
        int editionPrice = edition.getEditionPrice().movePointRight(2).intValue();
        int totalPrice = (int) (editionPrice * plan.getAmountOfMonths() * plan.getRate());
        return BigDecimal.valueOf(totalPrice).movePointLeft(2);
    }

    private PeriodicalEdition getEdition(HttpServletRequest request) {
        System.out.print(request.getParameter(EDITION));
        int editionId = Integer.parseInt(request.getParameter(EDITION));
        PeriodicalDao periodicalDao = MysqlDaoFactory.getInstance().getPeriodicalDao();
        return periodicalDao.findById(editionId);
    }

    private SubscriptionPlan getPlan(HttpServletRequest request) {
        String planName = request.getParameter(PLAN);
        return SubscriptionPlan.valueOf(planName);
    }

}
