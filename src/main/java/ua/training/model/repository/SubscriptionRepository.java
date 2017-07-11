package ua.training.model.repository;

import ua.training.model.dto.SubscriptionDTO;

import java.util.List;

/**
 * Data Access object for user_subscriptions table.
 *
 * @author Olksandr Mukhopad
 */
public interface SubscriptionRepository {
    /**
     * Finds all Subscriptions of certain user by his username.
     * @param username username
     * @return List of subscriptions
     */
    List<SubscriptionDTO> findByUsername(String username);

    /**
     * Finds all Subscriptions of certain edition by its id.
     * @param editionId id of edition
     * @return List of subscriptions
     */
    List<SubscriptionDTO> findByPeriodical(int editionId);

    /**
     * Finds all Subscriptions of certain transaction by its id.
     * @param transactionId id of edition
     * @return List of subscriptions
     */
    List<SubscriptionDTO> findByTransactionNumber(int transactionId);

    /**
     * Tries to insert subscription into database;
     * @param subscription subscription
     * @return true if insertion was successful, false otherwise
     */
    boolean insert(SubscriptionDTO subscription);

    /**
     * Tries to update subscription into database;
     * @param subscription subscription
     * @return true if update was successful, false otherwise
     */
    boolean update(SubscriptionDTO subscription);
}
