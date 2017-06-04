package ua.training.model.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private User user;
    private Timestamp transactionTime;
    private BigDecimal totalPrice;
    private String status;

    public Transaction(int transactionId, User user, Timestamp transactionTime, BigDecimal totalPrice, String status) {
        this.transactionId = transactionId;
        this.user = user;
        this.transactionTime = transactionTime;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public String getStatus() {return status;}

    public Transaction setStatus(String status) {
        this.status = status;
        return this;
    }
}
