package ua.training.model.services;

import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest {
    @Test
    public void hashPassword() {
        UserService service = UserService.getInstance();
        String password1 = new String("qwerty");
        password1 = service.hashPassword(password1);

        String password2 = new String("qwerty");
        password2 = service.hashPassword(password2);

        Assert.assertEquals(password1, password2);
    }

}