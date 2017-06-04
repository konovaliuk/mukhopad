package ua.training.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.User;
import ua.training.util.Log;
import ua.training.util.Message;
import ua.training.util.Pages;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SessionManager {
    private static final Logger LOGGER = LogManager.getLogger(SessionManager.class);
    private static final String SESSION_USER = "user";
    private static final String SESSION_PERIODICALS = "subscriptions";

    private static final String PARAM_ERROR = "error";
    private static final String PARAM_SUCCESS = "success";

    public static String loadUserDataToSession(HttpServletRequest request, String login) {
        LOGGER.info(Log.LOADING_SESSION_DATA);
        UserDao userDao = MysqlDaoFactory.getInstance().getUserDao();
        User user = userDao.findByUsername(login);
        request.getSession().setAttribute(SESSION_USER, user);

        loadSubscriptionData(request, user);
        return Pages.getInstance().get(Pages.MAIN);
    }

    public static void loadSubscriptionData(HttpServletRequest request, User user) {
        LOGGER.info(Log.LOADING_SUBSCRIPTION_DATA);
        SubscriptionDao subscriptionDao = MysqlDaoFactory.getInstance().getSubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);
    }

    public static String userError(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_ERROR, Message.getProperty(message));
        return Pages.getInstance().get(redirectPage);
    }

    public static String userSuccess(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_SUCCESS, Message.getProperty(message));
        return Pages.getInstance().get(redirectPage);
    }

}
