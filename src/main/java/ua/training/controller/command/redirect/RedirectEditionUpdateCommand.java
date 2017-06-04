package ua.training.controller.command.redirect;

import ua.training.controller.command.Command;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectEditionUpdateCommand implements Command {
    private static final String EDITION = "edition";
    private static final String ACTION_PARAM = "action";
    private static final String ACTION = "update";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodicalEdition edition = getEdition(request);
        request.setAttribute(EDITION, edition);
        request.setAttribute(ACTION_PARAM, ACTION);
        return Page.get(Page.EDIT);
    }

    private PeriodicalEdition getEdition(HttpServletRequest request) {
        int editionId = Integer.parseInt(request.getParameter(EDITION));
        PeriodicalDao periodicalDao = MysqlDaoFactory.getInstance().getPeriodicalDao();
        return periodicalDao.findById(editionId);
    }
}
