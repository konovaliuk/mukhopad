package ua.training.model.entities;

public enum SubscriptionPlan {
    ONE_MONTH(1, 1),
    THREE_MONTHS(3, 0.9),
    SIX_MONTHS(6, 0.8),
    YEAR(12, 0.75);

    private int amountOfMonths;
    private double rate;

    SubscriptionPlan(int amountOfMonths, double rate) {
        this.amountOfMonths = amountOfMonths;
        this.rate = rate;
    }

    public int getAmountOfMonths() {
        return amountOfMonths;
    }

    public double getRate() {
        return rate;
    }
}
