package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest {
    @Test
    public void hashSamePassword() {
        UserService service = UserService.getService();
        String password1 = new String("qwerty");
        password1 = service.hashPassword(password1);

        String password2 = new String("qwerty");
        password2 = service.hashPassword(password2);

        Assert.assertEquals(password1, password2);
    }

    @Test
    public void hashDifferentPassword() {
        UserService service = UserService.getService();
        String password1 = new String("qwerty");
        password1 = service.hashPassword(password1);

        String password2 = new String("password");
        password2 = service.hashPassword(password2);

        Assert.assertNotEquals(password1, password2);
    }

    @Test
    public void testSafeInputCharactersAndDashUnderscoreInjection() {
        UserService service = UserService.getService();
        String input = "<script>$('body-main')</script><b>Hello World!</b>@$#";
        String actual = service.passwordSafeInput(input);
        String expected = "body-mainhelloworld";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testSafeInputCharactersAndDashUnderscore() {
        UserService service = UserService.getService();
        String input = "Vasya_pupkin-2001";
        String actual = service.passwordSafeInput(input);
        String expected = "vasya_pupkin-2001";
        Assert.assertEquals(expected, actual);
    }
}