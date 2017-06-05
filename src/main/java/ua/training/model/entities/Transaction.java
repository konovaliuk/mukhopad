package ua.training.model.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private User user;
    private Timestamp transactionTime;
    private BigDecimal totalPrice;

    public Transaction(int transactionId, User user, Timestamp transactionTime, BigDecimal totalPrice) {
        this.transactionId = transactionId;
        this.user = user;
        this.transactionTime = transactionTime;
        this.totalPrice = totalPrice;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
