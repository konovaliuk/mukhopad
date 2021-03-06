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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (getTransactionId() != that.getTransactionId()) return false;
        if (!getUser().equals(that.getUser())) return false;
        if (!getTransactionTime().equals(that.getTransactionTime())) return false;
        return getTotalPrice().equals(that.getTotalPrice());
    }

    @Override
    public int hashCode() {
        int result = getTransactionId();
        result = 31 * result + getUser().hashCode();
        result = 31 * result + getTransactionTime().hashCode();
        result = 31 * result + getTotalPrice().hashCode();
        return result;
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

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", user=" + user +
                ", transactionTime=" + transactionTime +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
