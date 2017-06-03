package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.model.services.UserService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

import static ua.training.controller.command.CommandUtil.*;

public class UserLoginCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        if (UserService.getInstance()
                .login(login, password)){
            return loadUserDataToSession(request, login);
        } else {
            return userError(request, Message.LOGIN_ERROR, Config.LOGIN);
        }
    }
}
