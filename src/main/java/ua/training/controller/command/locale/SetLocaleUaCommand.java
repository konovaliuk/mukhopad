package ua.training.controller.command.locale;

import ua.training.controller.command.Command;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SetLocaleUaCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Message.setLocale("ua");
        return Pages.getInstance().get(Pages.MAIN);
    }
}
