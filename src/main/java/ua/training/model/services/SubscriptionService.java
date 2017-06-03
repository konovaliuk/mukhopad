package ua.training.model.services;

import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;

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

    public boolean subscribeUser(User user, PeriodicalEdition edition, SubscriptionPlan plan) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Transaction transaction = doTransaction(user, currentTime, edition, plan);

        Timestamp expirationDate = calculateExpirationDate(currentTime, plan);
        return DAO.insert(new Subscription(user, edition, transaction, expirationDate));
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

    public BigDecimal calculateTotalPrice(PeriodicalEdition edition, SubscriptionPlan plan) {
        int editionPrice = edition.getEditionPrice().movePointRight(2).intValue();
        int totalPrice = (int) (editionPrice * plan.getAmountOfMonths() * plan.getRate());
        return BigDecimal.valueOf(totalPrice).movePointLeft(2);
    }

}
