package ua.training.controller.command.periodical;

import ua.training.controller.command.Command;
import ua.training.model.services.PeriodicalsService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class PeriodicalDeleteCommand implements Command {
    private static final String EDITION_ID = "edition";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(EDITION_ID));
        if (PeriodicalsService.getService()
                .deletePeriodical(id)) {
            return Message.success(request, Message.PERIODICAL_DELETE_SUCCESS, Page.MAIN);
        } else {
            return Message.error(request, Message.PERIODICAL_DELETE_ERROR, Page.MAIN);
        }
    }
}
