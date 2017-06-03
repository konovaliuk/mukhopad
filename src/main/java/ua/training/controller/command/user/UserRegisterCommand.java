package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.model.services.UserService;
import ua.training.util.Pages;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.training.controller.SessionManager.*;

public class UserRegisterCommand implements Command {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String EMAIL = "email";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        String email = request.getParameter(EMAIL);

        if (!password.equals(confirmPassword)) {
            return userError(request, Message.PASSWORD_MISMATCH_ERROR, Pages.REGISTRATION);
        }

        if (UserService.getInstance()
                .register(login, password, email)){
            return userSuccess(request, Message.REGISTRATION_SUCCESS, Pages.LOGIN);
        } else {
            return userError(request, Message.REGISTRATION_ERROR, Pages.REGISTRATION);
        }
    }
}
