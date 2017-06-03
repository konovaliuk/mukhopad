package ua.training.controller;

import ua.training.controller.command.Command;
import ua.training.controller.command.NoCommand;
import ua.training.controller.command.locale.SetLocaleEnCommand;
import ua.training.controller.command.locale.SetLocaleUaCommand;
import ua.training.controller.command.periodical.PeriodicalInsertCommand;
import ua.training.controller.command.periodical.PeriodicalListAllCommand;
import ua.training.controller.command.periodical.PeriodicalUpdateCommand;
import ua.training.controller.command.redirect.RedirectCheckoutCommand;
import ua.training.controller.command.redirect.RedirectRegisterCommand;
import ua.training.controller.command.redirect.RedirectUserPageCommand;
import ua.training.controller.command.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CommandManager {

    private static HashMap<String, Command> commands = new HashMap<>();
    static {
        commands.put("noCommand", new NoCommand());

        /* User commands */
        commands.put("userLogin", new UserLoginCommand());
        commands.put("userLogout", new UserLogoutCommand());
        commands.put("userRegister", new UserRegisterCommand());
        commands.put("userSubscribe", new UserSubscribeCommand());
        commands.put("listUserSubscriptions", new UserSubscriptionListCommand());

        /* Locale commands */
        commands.put("localeUa", new SetLocaleUaCommand());
        commands.put("localeEn", new SetLocaleEnCommand());

        /* Redirect commands */
        commands.put("redirectRegister", new RedirectRegisterCommand());
        commands.put("redirectCheckout", new RedirectCheckoutCommand());
        commands.put("redirectEditionUpdate", new RedirectEditionUpdateCommand());
        commands.put("redirectEditionAdd", new RedirectEditionAddCommand());
        commands.put("redirectUserPage", new RedirectUserPageCommand());

        /* Periodical commands */
        commands.put("listPeriodicals", new PeriodicalListAllCommand());
        commands.put("addPeriodical", new PeriodicalInsertCommand());
        commands.put("updatePeriodical", new PeriodicalUpdateCommand());
    }

    static Command getCommand(HttpServletRequest request) {
        Command command = commands.get(request.getParameter("command"));
        if (command == null) {
            command =  commands.get("noCommand");
        }
        return command;
    }
}
