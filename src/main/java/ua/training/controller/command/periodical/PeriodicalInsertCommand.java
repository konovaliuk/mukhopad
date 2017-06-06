package ua.training.controller.command.periodical;

import ua.training.controller.command.Command;
import ua.training.model.services.PeriodicalsService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class PeriodicalInsertCommand implements Command {
    private static final String EDITION_NAME = "editionName";
    private static final String EDITION_PRICE = "editionPrice";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter(EDITION_NAME);
        BigDecimal price = new BigDecimal(request.getParameter(EDITION_PRICE));

        if (PeriodicalsService.getService()
                .addPeriodical(name, price)) {
            return Message.success(request, Message.PERIODICAL_INSERTION_SUCCESS, Page.MAIN);
        } else {
            return Message.error(request, Message.PERIODICAL_INSERTION_ERROR, Page.MAIN);
        }
    }
}
