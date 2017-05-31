package ua.training.controller.command;

import ua.training.model.services.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSubscribeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SubscriptionService.getInstance().subscribeUser(request, response);
        return null;
    }
}
