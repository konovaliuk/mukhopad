package ua.training.model.repository;

/**
 * Interface that mplements AbstractFactory GoF Pattern.
 * Contains getters of every entity REPOSITORY.
 * @author Oleksandr Mukhopad
 */
public interface RepositoryFactory {

    /**
     * Returns implementation of PeriodicalRepository.
     * @return PeriodicalRepository implementation.
     */
    PeriodicalRepository getPeriodicalRepository();

    /**
     * Returns implementation of SubscriptionRepository.
     * @return SubscriptionRepository implementation.
     */
    SubscriptionRepository getSubscriptionRepository();

    /**
     * Returns implementation of TransactionRepository.
     * @return TransactionRepository implementation.
     */
    TransactionRepository getTransactionRepository();

    /**
     * Returns implementation of UserRepository.
     * @return UserRepository implementation.
     */
    UserRepository getUserRepository();
}
