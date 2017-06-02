package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.model.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return UserService.getInstance().login(request, response);
    }
}
