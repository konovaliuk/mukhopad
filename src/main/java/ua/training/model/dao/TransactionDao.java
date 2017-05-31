package ua.training.model.dao;

import ua.training.model.entities.Transaction;

import java.util.List;

public interface TransactionDao {
    List<Transaction> findAll();

    List<Transaction> findByUsername(String username);

    Transaction findById(int Id);

    boolean insert(Transaction user);
}
