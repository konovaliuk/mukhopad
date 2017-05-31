package ua.training.model.dao.mysql;

import ua.training.model.dao.*;

public class MysqlDaoFactory implements DaoFactory {
    private static final MysqlDaoFactory FACTORY = new MysqlDaoFactory();

    private MysqlDaoFactory(){}

    public static MysqlDaoFactory getInstance() {
        return FACTORY;
    }

    @Override
    public PeriodicalDao getPeriodicalDao() {
        return MysqlPeriodicalDao.getInstance();
    }

    @Override
    public SubscriptionDao getSubscriptionDao() {
        return MysqlSubscriptionDao.getInstance();
    }

    @Override
    public TransactionDao getTransactionDao() {
        return MysqlTransactionDao.getInstance();
    }

    @Override
    public UserDao getUserDao() {
        return MysqlUserDao.getInstance();
    }
}
