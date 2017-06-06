package ua.training.model.entities;

import java.math.BigDecimal;

public class PeriodicalEdition {
    private int editionId;
    private String editionName;
    private BigDecimal editionPrice;

    public PeriodicalEdition(int editionId, String editionName, BigDecimal editionPrice) {
        this.editionId = editionId;
        this.editionName = editionName;
        this.editionPrice = editionPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PeriodicalEdition edition = (PeriodicalEdition) o;

        if (getEditionId() != edition.getEditionId()) return false;
        if (!getEditionName().equals(edition.getEditionName())) return false;
        return getEditionPrice().equals(edition.getEditionPrice());
    }

    @Override
    public int hashCode() {
        int result = getEditionId();
        result = 31 * result + getEditionName().hashCode();
        result = 31 * result + getEditionPrice().hashCode();
        return result;
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }

    public String getEditionName() {
        return editionName;
    }

    public void setEditionName(String editionName) {
        this.editionName = editionName;
    }

    public BigDecimal getEditionPrice() {
        return editionPrice;
    }

    public void setEditionPrice(BigDecimal editionPrice) {
        this.editionPrice = editionPrice;
    }

    @Override
    public String toString() {
        return "PeriodicalEdition{" +
                "editionId=" + editionId +
                ", editionName='" + editionName + '\'' +
                ", editionPrice=" + editionPrice +
                '}';
    }
}
