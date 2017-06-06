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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (!getUser().equals(that.getUser())) return false;
        if (!getEdition().equals(that.getEdition())) return false;
        if (!getTransaction().equals(that.getTransaction())) return false;
        return getExpirationDate().equals(that.getExpirationDate());
    }

    @Override
    public int hashCode() {
        int result = getUser().hashCode();
        result = 31 * result + getEdition().hashCode();
        result = 31 * result + getTransaction().hashCode();
        result = 31 * result + getExpirationDate().hashCode();
        return result;
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

    @Override
    public String toString() {
        return "Subscription{" +
                "user=" + user +
                ", edition=" + edition +
                ", transaction=" + transaction +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
