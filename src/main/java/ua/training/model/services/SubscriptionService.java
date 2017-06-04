package ua.training.model.services;

import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;
import ua.training.util.Log;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class SubscriptionService {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SubscriptionService.class);
    private static final SubscriptionDao SUBSCRIPTION_DAO = MysqlDaoFactory.getInstance().getSubscriptionDao();
    private static final TransactionDao TRANSACTION_DAO = MysqlDaoFactory.getInstance().getTransactionDao();
    private static SubscriptionService instance;

    private static final String STATUS_OK = "OK";
    private static final String STATUS_FAIL = "FAIL";

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

        Subscription subscription = new Subscription(user, edition, transaction, expirationDate);
        if(SUBSCRIPTION_DAO.insert(subscription)) {
            LOGGER.info(Log.USER_SUBSCRIBED);
            return TRANSACTION_DAO.update(transaction.setStatus(STATUS_OK));
        }
        return false;

    }

    private Transaction doTransaction(User user, Timestamp currentTime, PeriodicalEdition edition, SubscriptionPlan plan) {
        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        Transaction transaction = new Transaction(TRANSACTION_DAO.tableSize() + 1, user, currentTime, totalPrice, STATUS_FAIL);
        LOGGER.info(Log.TRANSACTION_CREATED);
        TRANSACTION_DAO.insert(transaction);
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
