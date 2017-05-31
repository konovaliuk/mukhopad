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
}
