package ua.training.model.entities;

import java.sql.Timestamp;

public class Subscription {
    private User user;
    private PeriodicalEdition edition;
    private Transaction transaction;
    private Timestamp expirationDate;

    public Subscription(User user, PeriodicalEdition edition, Transaction transaction, Timestamp expirationDate) {
        this.user = user;
        this.edition = edition;
        this.transaction = transaction;
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PeriodicalEdition getEdition() {
        return edition;
    }

    public void setEdition(PeriodicalEdition edition) {
        this.edition = edition;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }
}
