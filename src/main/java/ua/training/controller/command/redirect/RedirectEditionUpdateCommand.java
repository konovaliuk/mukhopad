package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class RedirectEditionUpdateCommand implements Command {
    private static final String EDITION = "edition";
    private static final String ACTION_PARAM = "action";
    private static final String ACTION = "update";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodicalEditionDTO edition = getEdition(request);
        request.setAttribute(EDITION, edition);
        request.setAttribute(ACTION_PARAM, ACTION);
        return Page.get(Page.EDIT);
    }

    private PeriodicalEditionDTO getEdition(HttpServletRequest request) {
        int editionId = Integer.parseInt(request.getParameter(EDITION));
        PeriodicalRepository periodicalRepository = MysqlRepositoryFactory.getInstance().getPeriodicalRepository();
        return periodicalRepository.findById(editionId);
    }
}
