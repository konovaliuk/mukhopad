package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.model.services.UserService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UserRegisterCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String EMAIL = "email";

    private static final String TEMP_USER_PARAM = "tempUsername";
    private static final String TEMP_EMAIL_PARAM = "tempEmail";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        String email = request.getParameter(EMAIL);

        if (!password.equals(confirmPassword)) {
            return Message.error(request, Message.PASSWORD_MISMATCH_ERROR, Page.REGISTRATION);
        }

        if (UserService.getService()
                .register(login, password, email)){
            return Message.success(request, Message.REGISTRATION_SUCCESS, Page.LOGIN);
        } else {
            request.getSession().setAttribute(TEMP_USER_PARAM, login);
            request.getSession().setAttribute(TEMP_EMAIL_PARAM, email);
            return Message.error(request, Message.REGISTRATION_ERROR, Page.REGISTRATION);
        }
    }
}
