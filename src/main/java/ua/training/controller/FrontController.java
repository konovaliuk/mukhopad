package ua.training.controller;

import org.apache.logging.log4j.*;
import ua.training.controller.command.Command;
import ua.training.controller.command.periodical.PeriodicalListAllCommand;
import ua.training.util.Log;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(FrontController.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException  {
        String page = null;
        try {
            Command command = CommandManager.getCommand(request);
            page = command.execute(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.error(e.getMessage());
        }

        new PeriodicalListAllCommand().execute(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        LOGGER.info(Log.REDIRECT_TO + page);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        processRequest(request, response);
    }
}
