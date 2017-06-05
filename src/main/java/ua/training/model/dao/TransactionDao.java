package ua.training.model.dao;

import ua.training.model.entities.Transaction;

import java.util.List;

/**
 * Data Access object for transaction table.
 *
 * @author Olksandr Mukhopad
 */
public interface TransactionDao {
    /**
     * Finds all Transactions in database.
     * @return List of transactions.
     */
    List<Transaction> findAll();

    /**
     * Finds all Transactions of certain user by his username.
     * @param username name of user
     * @return List of transactions.
     */
    List<Transaction> findByUsername(String username);

    /**
     * Finds all Transactions of certain user by his username.
     * @param transactionId id of transaction
     * @return List of transactions.
     */
    Transaction findById(int transactionId);

    /**
     * Returns size of table
     * @return size of table
     */
    int tableSize();

    /**
     * Tries to insert transaction into database;
     * @param transaction transaction
     * @return true if insertion was successful, false otherwise
     */
    boolean insert(Transaction transaction);
}
