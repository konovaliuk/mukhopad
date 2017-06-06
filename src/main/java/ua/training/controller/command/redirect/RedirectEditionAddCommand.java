package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RedirectEditionAddCommand implements Command {
    private static final String ACTION_PARAM = "action";
    private static final String ACTION = "insert";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(ACTION_PARAM, ACTION);
        return Page.get(Page.EDIT);
    }
}
