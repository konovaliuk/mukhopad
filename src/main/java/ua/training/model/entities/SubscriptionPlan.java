package ua.training.model.entities;

public enum SubscriptionPlan {
    ONE_MONTH(" ONE_MONTH", 1, 1, "1 Month"),
    THREE_MONTHS("THREE_MONTHS", 3, 0.9, "3 Months - 10% OFF"),
    SIX_MONTHS("SIX_MONTHS", 6, 0.85, "6 Months - 15% OFF"),
    YEAR("YEAR", 12, 0.75, "1 Year - 25% OFF");

    private String name;
    private int amountOfMonths;
    private double rate;
    private String description;

    SubscriptionPlan(String name, int amountOfMonths, double rate, String description) {
        this.name = name;
        this.amountOfMonths = amountOfMonths;
        this.rate = rate;
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public int getAmountOfMonths() {
        return amountOfMonths;
    }

    public double getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }
}
