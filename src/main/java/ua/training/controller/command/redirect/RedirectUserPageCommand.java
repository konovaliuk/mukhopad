package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectUserPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return Page.getInstance().get(Page.USER);
    }
}
