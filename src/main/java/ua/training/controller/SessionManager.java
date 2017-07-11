package ua.training.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.repository.SubscriptionRepository;
import ua.training.model.repository.UserRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.SubscriptionDTO;
import ua.training.model.dto.UserDTO;
import ua.training.util.Log;
import ua.training.util.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Contains methods that upload certain piece of user data to session.
 * @author Oleksandr Mukhopad
 */
public class SessionManager {
    private static final Logger LOGGER = LogManager.getLogger(SessionManager.class);
    private static final String SESSION_USER = "user";
    private static final String SESSION_PERIODICALS = "subscriptions";

    /**
     * Uploads user data the session by invoking user REPOSITORY and binding it to session parameter.
     * @param request http request from servlet
     * @param login user login as string
     * @return absolute address of main page.
     */
    public static String loadUserDataToSession(HttpServletRequest request, String login) {
        LOGGER.info(Log.LOADING_SESSION_DATA);
        UserRepository userRepository = MysqlRepositoryFactory.getInstance().getUserRepository();
        UserDTO user = userRepository.findByUsername(login);
        request.getSession().setAttribute(SESSION_USER, user);

        loadSubscriptionData(request, user);
        return Page.get(Page.MAIN);
    }

    /**
     * Uploads user subscriptions data by invoking subscription REPOSITORY and finding
     * all user's subscriptions by his username and binding it to session parameter.
     * @param request http request from servlet
     * @param user warped user object
     */
    public static void loadSubscriptionData(HttpServletRequest request, UserDTO user) {
        LOGGER.info(Log.LOADING_SUBSCRIPTION_DATA);
        SubscriptionRepository subscriptionRepository = MysqlRepositoryFactory.getInstance().getSubscriptionRepository();
        List<SubscriptionDTO> subscriptions = subscriptionRepository.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);
    }
}
