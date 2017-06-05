package ua.training.model.dao;

import ua.training.model.entities.Subscription;

import java.util.List;

/**
 * Data Access object for user_subscriptions table.
 *
 * @author Olksandr Mukhopad
 */
public interface SubscriptionDao {
    /**
     * Finds all Subscriptions of certain user by his username.
     * @param username username
     * @return List of subscriptions
     */
    List<Subscription> findByUsername(String username);

    /**
     * Finds all Subscriptions of certain edition by its id.
     * @param editionId id of edition
     * @return List of subscriptions
     */
    List<Subscription> findByPeriodical(int editionId);

    /**
     * Finds all Subscriptions of certain transaction by its id.
     * @param transactionId id of edition
     * @return List of subscriptions
     */
    List<Subscription> findByTransactionNumber(int transactionId);

    /**
     * Tries to insert subscription into database;
     * @param subscription subscription
     * @return true if insertion was successful, false otherwise
     */
    boolean insert(Subscription subscription);

    /**
     * Tries to update subscription into database;
     * @param subscription subscription
     * @return true if update was successful, false otherwise
     */
    boolean update(Subscription subscription);
}
