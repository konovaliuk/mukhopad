package ua.training.controller.command.locale;

import ua.training.controller.command.Command;
import ua.training.util.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.Locale;

public class SetLocaleUaCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Config.set(request.getSession(), Config.FMT_LOCALE, new Locale("uk", "UA"));
        return Pages.getInstance().get(Pages.MAIN);
    }
}
