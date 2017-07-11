package ua.training.controller.command.user;

import ua.training.controller.SessionManager;
import ua.training.controller.command.Command;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.*;
import ua.training.model.services.SubscriptionService;
import ua.training.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UserSubscribeCommand implements Command {
    private static final String SESSION_USER = "user";
    private static final String EDITION = "edition";
    private static final String PLAN = "plan";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDTO user = (UserDTO) request.getSession().getAttribute(SESSION_USER);
        PeriodicalEditionDTO edition = getEdition(request);
        SubscriptionPlanDTO plan = getPlan(request);

        if(SubscriptionService.getService()
                .subscribeUser(user, edition, plan)){
            SessionManager.loadSubscriptionData(request, user);
            return Message.success(request, Message.USER_SUBSCRIBED, Page.MAIN);
        } else {
            return Message.error(request, Message.SUBSCRIPTION_ERROR, Page.MAIN);
        }
    }

    private PeriodicalEditionDTO getEdition(HttpServletRequest request) {
        int editionId = Integer.parseInt(request.getParameter(EDITION));
        PeriodicalRepository periodicalRepository = MysqlRepositoryFactory.getInstance().getPeriodicalRepository();
        return periodicalRepository.findById(editionId);
    }

    private SubscriptionPlanDTO getPlan(HttpServletRequest request) {
        String planName = request.getParameter(PLAN);
        return SubscriptionPlanDTO.valueOf(planName);
    }


}
