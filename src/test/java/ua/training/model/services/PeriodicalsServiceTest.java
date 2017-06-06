package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;

public class PeriodicalsServiceTest {
    @Test
    public void testSafeInput() {
        PeriodicalsService service = PeriodicalsService.getService();
        String input = "<script>$('body')</script>Hello World!@$";
        String actual = service.safeInput(input);
        String expected = "(body)Hello World!";
        Assert.assertEquals(expected, actual);
    }
}