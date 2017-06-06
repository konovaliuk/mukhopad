package ua.training.controller;

import org.apache.logging.log4j.*;
import ua.training.controller.command.*;
import ua.training.controller.command.locale.*;
import ua.training.controller.command.periodical.*;
import ua.training.controller.command.redirect.*;
import ua.training.controller.command.user.*;
import ua.training.util.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Command manager fetches "command" property from from request and takes appropriate command from Map.
 * @author Oleksandr Mukhopad
 */
class CommandManager {
    private static final Logger LOGGER = LogManager.getLogger(CommandManager.class);
    private static HashMap<String, Command> commands = new HashMap<>();
    static {
        commands.put("defaultCommand", new NoCommand());

        /* User commands */
        commands.put("userLogin", new UserLoginCommand());
        commands.put("userLogout", new UserLogoutCommand());
        commands.put("userRegister", new UserRegisterCommand());
        commands.put("userSubscribe", new UserSubscribeCommand());

        /* Locale commands */
        commands.put("localeUa", new SetLocaleUaCommand());
        commands.put("localeEn", new SetLocaleEnCommand());

        /* Redirect commands */
        commands.put("redirectMain", new RedirectMainPageCommand());
        commands.put("redirectRegister", new RedirectRegisterCommand());
        commands.put("redirectCheckout", new RedirectCheckoutCommand());
        commands.put("redirectEditionUpdate", new RedirectEditionUpdateCommand());
        commands.put("redirectEditionAdd", new RedirectEditionAddCommand());
        commands.put("redirectAdminPage", new RedirectAdminPageCommand());
        commands.put("redirectUserPage", new RedirectUserPageCommand());

        /* Periodical commands */
        commands.put("deletePeriodical", new PeriodicalDeleteCommand());
        commands.put("listPeriodicals", new PeriodicalListAllCommand());
        commands.put("addPeriodical", new PeriodicalInsertCommand());
        commands.put("updatePeriodical", new PeriodicalUpdateCommand());
    }

    static Command getCommand(HttpServletRequest request) {
        Command command = commands.get(request.getParameter("command"));
        if (command == null || request.getSession(false) == null) {
            command =  commands.get("defaultCommand");
        }
        LOGGER.info(Log.COMMAND_CALLED + command.getClass().getSimpleName());
        return command;
    }
}
