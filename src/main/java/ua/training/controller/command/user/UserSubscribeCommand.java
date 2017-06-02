package ua.training.controller.command.user;

import ua.training.controller.command.Command;
import ua.training.model.services.SubscriptionService;
import ua.training.model.services.UserService;
import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSubscribeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       SubscriptionService service = SubscriptionService.getInstance();
        if(service.subscribeUser(request, response)){
            return UserService.getInstance().userSuccess(request, Message.USER_SUBSCRIBED, Config.MAIN);
        } else {
            return UserService.getInstance().userError(request, Message.SUBSCRIPTION_ERROR, Config.MAIN);
        }
    }
}
