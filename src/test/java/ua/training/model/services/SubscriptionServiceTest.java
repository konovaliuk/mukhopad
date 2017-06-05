package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.model.entities.SubscriptionPlan;

import java.math.BigDecimal;

public class SubscriptionServiceTest {
    @Test
    public void calculateTotalPrice() {
        PeriodicalEdition edition = new PeriodicalEdition(1, "Foo", new BigDecimal("14.99"));
        SubscriptionPlan plan = SubscriptionPlan.SIX_MONTHS;

        BigDecimal actual = SubscriptionService.getService()
                .calculateTotalPrice(edition, plan);
        int intValue = edition.getEditionPrice().movePointRight(2).intValue();
        BigDecimal expected = new BigDecimal("" + (int) (intValue * 6 * 0.85)).movePointLeft(2);

        Assert.assertEquals(actual, expected);
    }

}