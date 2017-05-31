package ua.training.model.dao;

import ua.training.model.entities.Subscription;

import java.util.List;

public interface SubscriptionDao {
    List<Subscription> findByUsername(String username);

    List<Subscription> findByPeriodical(int periodicalId);

    List<Subscription> findByTransactionNumber(int transactionId);

    boolean insert(Subscription subscription);

    boolean update(Subscription subscription);
}
