package ua.training.controller;

import ua.training.controller.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class CommandManager {

    private static HashMap<String, Command> commands = new HashMap<>();
    static {
        commands.put("noCommand", new NoCommand());

        /* User commands */
        commands.put("userLogin", new UserLoginCommand());
        commands.put("userRegister", new UserRegisterCommand());
        commands.put("userSubscribe", new UserSubscribeCommand());
        commands.put("listUserSubscriptions", new UserSubscriptionListCommand());

        /* Periodical commands */
//        commands.put("listPeriodicals", new ListPeriodicalsCommand());
        commands.put("addPeriodical", new PeriodicalInsertCommand());
        commands.put("deletePeriodical", new PeriodicalDeleteCommand());
        commands.put("updatePeriodical", new PeriodicalUpdateCommand());
    }

    private CommandManager() {}

    public static Command getCommand(HttpServletRequest request) {
        Command command = commands.get((String) request.getSession().getAttribute("command"));
        if (command == null) {
            command =  commands.get("noCommand");
        }
        return command;
    }
}
