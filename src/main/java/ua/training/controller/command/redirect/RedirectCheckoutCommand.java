package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.services.SubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectCheckoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       return SubscriptionService.getInstance().proceedToCheckOut(request, response);
    }
}
