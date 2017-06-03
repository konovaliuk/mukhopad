package ua.training.controller.command.locale;

import ua.training.controller.command.Command;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SetLocaleEnCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Message.setLocale("en");
        return Pages.getInstance().get(Pages.MAIN);
    }
}
