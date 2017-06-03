package ua.training.controller.command.periodical;


import ua.training.controller.command.Command;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.util.Pages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PeriodicalListAllCommand implements Command {
    private static final String PERIODICALS_PARAM = "periodicals";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodicalDao dao = MysqlDaoFactory.getInstance().getPeriodicalDao();
        List<PeriodicalEdition> edition = dao.findAll();
            request.setAttribute(PERIODICALS_PARAM, edition);

        return Pages.getInstance().get(Pages.MAIN);
    }
}
