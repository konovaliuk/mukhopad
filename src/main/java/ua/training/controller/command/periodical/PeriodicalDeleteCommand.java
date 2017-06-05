package ua.training.controller.command.periodical;

import ua.training.controller.command.Command;
import ua.training.model.services.PeriodicalsService;
import ua.training.util.Message;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.training.controller.SessionManager.userError;
import static ua.training.controller.SessionManager.userSuccess;

public class PeriodicalDeleteCommand implements Command {
    private static final String EDITION_ID = "edition";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(EDITION_ID));
        if (PeriodicalsService.getService()
                .deletePeriodical(id)) {
            return userSuccess(request, Message.PERIODICAL_DELETE_SUCCESS, Page.MAIN);
        } else {
            return userError(request, Message.PERIODICAL_DELETE_ERROR, Page.MAIN);
        }
    }
}
