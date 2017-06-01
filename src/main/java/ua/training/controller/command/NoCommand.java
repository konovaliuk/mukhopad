package ua.training.controller.command;

import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            return Config.getInstance().getProperty(Config.MAIN);
        } else {
            request.setAttribute("error", Message.getInstance().getProperty(Message.ILLEGAL_ACCESS_ERROR));
            return Config.getInstance().getProperty(Config.LOGIN);
        }
    }
}
