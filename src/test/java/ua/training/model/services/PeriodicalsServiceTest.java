package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;

public class PeriodicalsServiceTest {
    @Test
    public void testSafeInputInjection() {
        PeriodicalsService service = PeriodicalsService.getService();
        String input = "<script>$('body')</script>Hello World!@$";
        String actual = service.safeInput(input);
        String expected = "(body)Hello World!";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSafeInputNormal() {
        PeriodicalsService service = PeriodicalsService.getService();
        String input = "Periodical Edition";
        String actual = service.safeInput(input);
        String expected = "Periodical Edition";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSafeInputWebLink() {
        PeriodicalsService service = PeriodicalsService.getService();
        String input = "http://google.com";
        String actual = service.safeInput(input);
        String expected = "http:google.com";
        Assert.assertEquals(expected, actual);
    }
}