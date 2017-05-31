package ua.training.controller.command;

import ua.training.model.services.PeriodicalsService;
import ua.training.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PeriodicalUpdateCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PeriodicalsService.getInstance().updatePeriodical(request, response);
        return Config.getInstance().getProperty(Config.MAIN);
    }
}
