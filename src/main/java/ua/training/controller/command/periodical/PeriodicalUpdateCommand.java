package ua.training.controller.command.periodical;

import ua.training.controller.command.Command;
import ua.training.model.services.PeriodicalsService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class PeriodicalUpdateCommand implements Command {
    private static final String EDITION_ID = "editionId";
    private static final String EDITION_NAME = "editionName";
    private static final String EDITION_PRICE = "editionPrice";
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(EDITION_ID));
        String name = (request.getParameter(EDITION_NAME));
        BigDecimal price = new BigDecimal(request.getParameter(EDITION_PRICE));

        if (PeriodicalsService.getService()
                .updatePeriodical(id, name, price)) {
            return Message.success(request, Message.PERIODICAL_UPDATE_SUCCESS, Page.MAIN);
        } else {
            return Message.error(request, Message.PERIODICAL_INSERTION_ERROR, Page.MAIN);
        }
    }
}
