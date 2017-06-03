package ua.training.controller.command.periodical;

import ua.training.controller.command.Command;
import ua.training.model.services.PeriodicalsService;
import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static ua.training.controller.command.CommandUtil.userError;
import static ua.training.controller.command.CommandUtil.userSuccess;

public class PeriodicalInsertCommand implements Command {
    private static final String EDITION_ID = "editionId";
    private static final String EDITION_NAME = "editionName";
    private static final String EDITION_PRICE = "editionPrice";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter(EDITION_ID));
        String name = (request.getParameter(EDITION_NAME));
        BigDecimal price = new BigDecimal(request.getParameter(EDITION_PRICE));

        if (PeriodicalsService.getInstance()
                .addPeriodical(id, name, price)) {
            return userSuccess(request, Message.PERIODICAL_INSERTION_SUCCESS, Config.MAIN);
        } else {
            return userError(request, Message.PERIODICAL_INSERTION_ERROR, Config.MAIN);
        }
    }
}
