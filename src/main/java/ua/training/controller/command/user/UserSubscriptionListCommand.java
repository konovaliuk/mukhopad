package ua.training.controller.command.user;

import ua.training.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UserSubscriptionListCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return null;
    }
}