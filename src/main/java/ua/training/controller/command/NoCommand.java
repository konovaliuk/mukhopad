package ua.training.controller.command;

import ua.training.controller.SessionManager;
import ua.training.model.entities.User;
import ua.training.util.Page;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    private static final String SESSION_USER = "user";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        if (user != null) {
            return SessionManager.loadUserDataToSession(request, user.getUsername());
        } else {
            return SessionManager.userError(request, Message.ILLEGAL_ACCESS_ERROR, Page.LOGIN);
        }
    }
}
