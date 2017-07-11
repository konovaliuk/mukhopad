package ua.training.model.repository.mysql;

import ua.training.model.repository.*;

public class MysqlRepositoryFactory implements RepositoryFactory {
    private static final MysqlRepositoryFactory FACTORY = new MysqlRepositoryFactory();

    private MysqlRepositoryFactory(){}

    public static MysqlRepositoryFactory getInstance() {
        return FACTORY;
    }

    @Override
    public PeriodicalRepository getPeriodicalRepository() {
        return MysqlPeriodicalRepository.getInstance();
    }

    @Override
    public SubscriptionRepository getSubscriptionRepository() {
        return MysqlSubscriptionRepository.getInstance();
    }

    @Override
    public TransactionRepository getTransactionRepository() {
        return MysqlTransactionRepository.getInstance();
    }

    @Override
    public UserRepository getUserRepository() {
        return MysqlUserRepository.getInstance();
    }
}
