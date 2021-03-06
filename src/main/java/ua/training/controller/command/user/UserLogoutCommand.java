package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.util.Message;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UserLogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Message.setDefaultLocale();
        return Page.get(Page.LOGIN);
    }
}
