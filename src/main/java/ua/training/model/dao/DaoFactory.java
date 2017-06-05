package ua.training.model.dao;

/**
 * Interface that mplements AbstractFactory GoF Pattern.
 * Contains getters of every entity DAO.
 * @author Oleksandr Mukhopad
 */
public interface DaoFactory {

    /**
     * Returns implementation of PeriodicalDao.
     * @return PeriodicalDao implementation.
     */
    PeriodicalDao getPeriodicalDao();

    /**
     * Returns implementation of SubscriptionDao.
     * @return SubscriptionDao implementation.
     */
    SubscriptionDao getSubscriptionDao();

    /**
     * Returns implementation of TransactionDao.
     * @return TransactionDao implementation.
     */
    TransactionDao getTransactionDao();

    /**
     * Returns implementation of UserDao.
     * @return UserDao implementation.
     */
    UserDao getUserDao();
}
