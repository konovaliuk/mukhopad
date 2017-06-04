package ua.training.controller.command.locale;

import ua.training.controller.command.Command;
import ua.training.util.Message;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

public class SetLocaleUaCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Message.setLocale(Message.UKRAINIAN);
        Config.set(request.getSession(), Config.FMT_LOCALE, Message.UKRAINIAN);
        return Page.get(Page.MAIN);
    }
}
