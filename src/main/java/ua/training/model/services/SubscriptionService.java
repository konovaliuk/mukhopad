package ua.training.model.services;

import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.model.repository.TransactionRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.*;
import ua.training.util.Log;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * SubscriptionService adds transactions to the data base and forms subscription based on user, periodical
 * and transaction. Has no state, so designed as Singleton.
 * @author Oleksandr Mukhopad
 */
@Service
public class SubscriptionService {
    private static final Logger LOGGER = org.apache.logging.log4j.LogManager.getLogger(SubscriptionService.class);
    private static final SubscriptionService SERVICE = new SubscriptionService();

    private SubscriptionService() {}

    public static SubscriptionService getService() {
        return SERVICE;
    }

    /**
     * Creates Subscription from passed parameters plus invokes methods
     * which calculate overall price and expiration date. Passes subscription to REPOSITORY
     * and return result of insertion.
     * @param user current user
     * @param edition periodical edition
     * @param plan subscription plan
     * @return true if subscription was inserted successfully, false otherwise
     */
    public boolean subscribeUser(UserDTO user, PeriodicalEditionDTO edition, SubscriptionPlanDTO plan) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        TransactionDTO transaction = doTransaction(user, currentTime, edition, plan);
        Timestamp expirationDate = calculateExpirationDate(currentTime, plan);

        SubscriptionDTO subscription = new SubscriptionDTO(user, edition, transaction, expirationDate);
        return MysqlRepositoryFactory.getInstance()
                .getSubscriptionRepository().insert(subscription);
    }

    /**
     * Creates transaction from input data
     * @param user current user
     * @param currentTime current time
     * @param edition periodical edition
     * @param plan subscription plan
     * @return created Transaction
     */
    private TransactionDTO doTransaction(UserDTO user, Timestamp currentTime, PeriodicalEditionDTO edition, SubscriptionPlanDTO plan) {
        BigDecimal totalPrice = calculateTotalPrice(edition, plan);
        TransactionRepository transactionRepository = MysqlRepositoryFactory.getInstance().getTransactionRepository();
        TransactionDTO transaction = new TransactionDTO(transactionRepository.tableSize() + 1, user, currentTime, totalPrice);
        LOGGER.info(Log.TRANSACTION_CREATED);
        transactionRepository.insert(transaction);
        return transaction;
    }

    /**
     * Adds plan's amount of days to the current date.
     * @param dateOfSubscription current date
     * @param plan subscription plan
     * @return Timestamp with estimated expiration date
     */
    private Timestamp calculateExpirationDate(Timestamp dateOfSubscription, SubscriptionPlanDTO plan) {
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
    public BigDecimal calculateTotalPrice(PeriodicalEditionDTO edition, SubscriptionPlanDTO plan) {
        int editionPrice = edition.getEditionPrice().movePointRight(2).intValue();
        int totalPrice = (int) (editionPrice * plan.getAmountOfMonths() * plan.getRate());
        return BigDecimal.valueOf(totalPrice).movePointLeft(2);
    }
}