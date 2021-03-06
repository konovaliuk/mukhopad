package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RedirectRegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return Page.get(Page.REGISTRATION);
    }
}
