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
    public void testSafeInputCharactersAndDashUnderscore() {
        UserService service = UserService.getService();
        String input = "<script>$('body-main')</script><b>Hello World!</b>@$#";
        String actual = service.safeInput(input);
        String expected = "body-mainHelloWorld";
        Assert.assertEquals(expected, actual);
    }

}