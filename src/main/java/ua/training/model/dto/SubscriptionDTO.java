package ua.training.model.dto;

import java.sql.Timestamp;

public class SubscriptionDTO {
    private UserDTO user;
    private PeriodicalEditionDTO edition;
    private TransactionDTO transaction;
    private Timestamp expirationDate;

    public SubscriptionDTO(UserDTO user, PeriodicalEditionDTO edition, TransactionDTO transaction, Timestamp expirationDate) {
        this.user = user;
        this.edition = edition;
        this.transaction = transaction;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionDTO that = (SubscriptionDTO) o;

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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PeriodicalEditionDTO getEdition() {
        return edition;
    }

    public void setEdition(PeriodicalEditionDTO edition) {
        this.edition = edition;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
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
