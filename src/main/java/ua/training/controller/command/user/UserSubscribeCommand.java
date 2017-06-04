package ua.training.controller.command.user;

import ua.training.controller.SessionManager;
import ua.training.controller.command.Command;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.model.entities.SubscriptionPlan;
import ua.training.model.entities.User;
import ua.training.model.services.SubscriptionService;
import ua.training.util.Message;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ua.training.controller.SessionManager.userError;
import static ua.training.controller.SessionManager.userSuccess;

public class UserSubscribeCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String EDITION = "edition";
    private static final String PLAN = "plan";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(SESSION_USER);
        PeriodicalEdition edition = getEdition(request);
        SubscriptionPlan plan = getPlan(request);

        if(SubscriptionService.getInstance()
                .subscribeUser(user, edition, plan)){
            SessionManager.loadSubscriptionData(request, user);
            return userSuccess(request, Message.USER_SUBSCRIBED, Page.MAIN);
        } else {
            return userError(request, Message.SUBSCRIPTION_ERROR, Page.MAIN);
        }
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
