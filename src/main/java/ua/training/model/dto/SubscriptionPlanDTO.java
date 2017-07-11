package ua.training.model.dto;

import ua.training.util.Message;

public enum SubscriptionPlanDTO {
    ONE_MONTH("ONE_MONTH", 1, 1, Message.EDITION_PLAN_ONE),
    THREE_MONTHS("THREE_MONTHS", 3, 0.9, Message.EDITION_PLAN_THREE),
    SIX_MONTHS("SIX_MONTHS", 6, 0.85, Message.EDITION_PLAN_SIX),
    YEAR("YEAR", 12, 0.75, Message.EDITION_PLAN_YEAR);

    private String name;
    private int amountOfMonths;
    private double rate;
    private String description;

    SubscriptionPlanDTO(String name, int amountOfMonths, double rate, String description) {
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

    @Override
    public String toString() {
        return "SubscriptionPlan{" +
                "name='" + name + '\'' +
                ", amountOfMonths=" + amountOfMonths +
                ", rate=" + rate +
                ", description='" + description + '\'' +
                '}';
    }
}
