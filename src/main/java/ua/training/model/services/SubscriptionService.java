package ua.training.model.services;

import org.apache.logging.log4j.Logger;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;
import ua.training.util.Log;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * SubscriptionService adds transactions to the data base and forms subscription based on user, periodical
 * and transaction. Has no state, so designed as Singleton.
 * @author Oleksandr Mukhopad
 */
public class SubscriptionService {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SubscriptionService.class);
    private static final SubscriptionService SERVICE = new SubscriptionService();

    private SubscriptionService() {}

    public static SubscriptionService getService() {
        return SERVICE;
    }

    /**
     * Creates Subscription from passed parameters plus invokes methods
     * which calculate overall price and expiration date. Passes subscription to DAO
     * and return result of insertion.
     * @param user current user
     * @param edition periodical edition
     * @param plan subscription plan
     * @return true if subscription was inserted successfully, false otherwise
     */
    public boolean subscribeUser(User user, PeriodicalEdition edition, SubscriptionPlan plan) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Transaction transaction = doTransaction(user, currentTime, edition, plan);
        Timestamp expirationDate = calculateExpirationDate(currentTime, plan);

        Subscription subscription = new Subscription(user, edition, transaction, expirationDate);
        return MysqlDaoFactory.getInstance()
                .getSubscriptionDao().insert(subscription);
    }

    /**
     * Creates transaction from input data
     * @param user current user
     * @param currentTime current time
     * @param edition periodical edition
     * @param plan subscription plan
     * @return created Transaction
     */
    private Transaction doTransaction(User user, Timestamp currentTime, PeriodicalEdition edition, SubscriptionPlan plan) {
        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        Transaction transaction = new Transaction(transactionDao.tableSize() + 1, user, currentTime, totalPrice);
        LOGGER.info(Log.TRANSACTION_CREATED);
        transactionDao.insert(transaction);
        return transaction;
    }

    /**
     * Adds plan's amount of days to the current date.
     * @param dateOfSubscription current date
     * @param plan subscription plan
     * @return Timestamp with estimated expiration date
     */
    private Timestamp calculateExpirationDate(Timestamp dateOfSubscription, SubscriptionPlan plan) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfSubscription);
        calendar.add(Calendar.MONTH, plan.getAmountOfMonths());
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * Calculates price by the formula:
     * TOTAL_PRICE = PERIODICAL_MONTHLY_PRICE x PLAN_MONTHS x PLAN_DISCOUNT_RATE
     * Converts BigDecimal to int by floating point shifting, does calculation
     * and converts back to BigDecimal with reverse shifting.
     * @param edition periodical edition
     * @param plan subscription plan
     * @return total price
     */
    public BigDecimal calculateTotalPrice(PeriodicalEdition edition, SubscriptionPlan plan) {
        int editionPrice = edition.getEditionPrice().movePointRight(2).intValue();
        int totalPrice = (int) (editionPrice * plan.getAmountOfMonths() * plan.getRate());
        return BigDecimal.valueOf(totalPrice).movePointLeft(2);
    }
}