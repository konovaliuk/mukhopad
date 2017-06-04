package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.model.entities.SubscriptionPlan;
import ua.training.model.services.SubscriptionService;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class RedirectCheckoutCommand implements Command {
    private static final String EDITION = "edition";
    private static final String PLAN = "plan";
    private static final String TOTAL_PRICE = "totalPrice";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubscriptionPlan plan = getPlan(request);
        request.setAttribute(PLAN, plan);

        PeriodicalEdition edition =  getEdition(request);
        request.setAttribute(EDITION, edition);

        BigDecimal totalPrice =
                SubscriptionService.getInstance().calculateTotalPrice(edition, plan);
        request.setAttribute(TOTAL_PRICE, totalPrice);

        return Page.get(Page.CHECKOUT);
    }

    private PeriodicalEdition getEdition(HttpServletRequest request) {
        int editionId = Integer.parseInt(request.getParameter(EDITION));
        PeriodicalDao periodicalDao = MysqlDaoFactory.getInstance().getPeriodicalDao();
        return periodicalDao.findById(editionId);
    }

    private SubscriptionPlan getPlan(HttpServletRequest request) {
        String planName = request.getParameter(PLAN);
        return SubscriptionPlan.valueOf(planName);
    }
}
