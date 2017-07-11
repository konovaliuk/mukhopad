package ua.training.model.repository;

import org.springframework.stereotype.Repository;
import ua.training.model.dto.TransactionDTO;

import java.util.List;

/**
 * Data Access object for transaction table.
 *
 * @author Olksandr Mukhopad
 */
@Repository
public interface TransactionRepository {
    /**
     * Finds all Transactions in database.
     * @return List of transactions.
     */
    List<TransactionDTO> findAll();

    /**
     * Finds all Transactions of certain user by his username.
     * @param username name of user
     * @return List of transactions.
     */
    List<TransactionDTO> findByUsername(String username);

    /**
     * Finds all Transactions of certain user by his username.
     * @param transactionId id of transaction
     * @return List of transactions.
     */
    TransactionDTO findById(int transactionId);

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
    boolean insert(TransactionDTO transaction);
}
