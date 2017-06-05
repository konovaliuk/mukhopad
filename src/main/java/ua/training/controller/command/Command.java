package ua.training.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implements Command GoF Pattern that brings client-server functionality.
 * @author Oleksandr Mukhopad
 */
public interface Command {

    /**
     * This method returns relative address of jsp file, which will be forwarded by servlet.
     * Also additional services inside method may be called when needed.
     *
     * @param request takes http request from servlet.
     * @param response takes http response from servlet.
     * @return relative address of .jsp file
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
