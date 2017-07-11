package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.*;
import ua.training.model.services.SubscriptionService;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class RedirectCheckoutCommand implements Command {
    private static final String EDITION = "edition";
    private static final String PLAN = "plan";
    private static final String TOTAL_PRICE = "totalPrice";
    private static final String ALREADY_SUBSCRIBED = "alreadySubscribed";

    private static final String SESSION_PERIODICALS = "subscriptions";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubscriptionPlanDTO plan = getPlan(request);
        request.setAttribute(PLAN, plan);

        PeriodicalEditionDTO edition =  getEdition(request);
        request.setAttribute(EDITION, edition);

        BigDecimal totalPrice =
                SubscriptionService.getService().calculateTotalPrice(edition, plan);
        request.setAttribute(TOTAL_PRICE, totalPrice);

        String expDate = userAlreadySubscribed(request, edition);
        if (expDate != null) {
            request.setAttribute(ALREADY_SUBSCRIBED, expDate);
        }

        return Page.get(Page.CHECKOUT);
    }

    private String userAlreadySubscribed(HttpServletRequest request, PeriodicalEditionDTO edition) {
        List<SubscriptionDTO> userSubscriptions =
                (List<SubscriptionDTO>) request.getSession().getAttribute(SESSION_PERIODICALS);
        for(SubscriptionDTO s : userSubscriptions) {
            String currentEditionName = s.getEdition().getEditionName();
            if (currentEditionName.equals(edition.getEditionName())){
                return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(s.getExpirationDate());
            }
        }
        return null;
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
