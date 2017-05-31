package ua.training.model.dao;

public interface DaoFactory {
    PeriodicalDao getPeriodicalDao();

    SubscriptionDao getSubscriptionDao();

    TransactionDao getTransactionDao();

    UserDao getUserDao();
}
