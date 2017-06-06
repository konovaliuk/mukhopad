package ua.training.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.User;
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
     * Uploads user data the session by invoking user DAO and binding it to session parameter.
     * @param request http request from servlet
     * @param login user login as string
     * @return absolute address of main page.
     */
    public static String loadUserDataToSession(HttpServletRequest request, String login) {
        LOGGER.info(Log.LOADING_SESSION_DATA);
        UserDao userDao = MysqlDaoFactory.getInstance().getUserDao();
        User user = userDao.findByUsername(login);
        request.getSession().setAttribute(SESSION_USER, user);

        loadSubscriptionData(request, user);
        return Page.get(Page.MAIN);
    }

    /**
     * Uploads user subscriptions data by invoking subscription DAO and finding
     * all user's subscriptions by his username and binding it to session parameter.
     * @param request http request from servlet
     * @param user warped user object
     */
    public static void loadSubscriptionData(HttpServletRequest request, User user) {
        LOGGER.info(Log.LOADING_SUBSCRIPTION_DATA);
        SubscriptionDao subscriptionDao = MysqlDaoFactory.getInstance().getSubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);
    }
}
