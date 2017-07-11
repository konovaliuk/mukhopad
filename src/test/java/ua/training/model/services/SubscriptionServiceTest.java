package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.model.dto.SubscriptionPlanDTO;

import java.math.BigDecimal;

public class SubscriptionServiceTest {
    @Test
    public void calculateTotalPrice() {
        PeriodicalEditionDTO edition = new PeriodicalEditionDTO(1, "Foo", new BigDecimal("14.99"));
        SubscriptionPlanDTO plan = SubscriptionPlanDTO.SIX_MONTHS;

        BigDecimal actual = SubscriptionService.getService()
                .calculateTotalPrice(edition, plan);
        int intValue = edition.getEditionPrice().movePointRight(2).intValue();
        BigDecimal expected = new BigDecimal("" + (int) (intValue * 6 * 0.85)).movePointLeft(2);

        Assert.assertEquals(actual, expected);
    }

}