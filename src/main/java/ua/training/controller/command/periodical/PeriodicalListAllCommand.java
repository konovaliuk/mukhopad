package ua.training.controller.command.periodical;


import ua.training.controller.command.Command;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class PeriodicalListAllCommand implements Command {
    private static final String PERIODICALS_PARAM = "periodicals";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PeriodicalRepository repository = MysqlRepositoryFactory.getInstance().getPeriodicalRepository();
        List<PeriodicalEditionDTO> edition = repository.findAll();
            request.setAttribute(PERIODICALS_PARAM, edition);

        return Page.get(Page.MAIN);
    }
}
