package ua.training.controller.command;

import ua.training.model.entities.User;
import ua.training.model.services.UserService;
import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            UserService.getInstance().loadUserDataToSession(request, user);
            return Config.getInstance().getProperty(Config.MAIN);
        } else {
            return UserService.getInstance()
                    .userError(request, Message.ILLEGAL_ACCESS_ERROR, Config.LOGIN);
        }
    }
}
