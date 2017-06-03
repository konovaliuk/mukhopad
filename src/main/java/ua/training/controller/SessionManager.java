package ua.training.controller;

import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.Transaction;
import ua.training.model.entities.User;
import ua.training.util.Message;
import ua.training.util.Pages;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SessionManager {
    private static final String SESSION_USER = "user";
    private static final String SESSION_PERIODICALS = "subscriptions";
    private static final String SESSION_TRANSACTIONS = "transactions";

    private static final String PARAM_ERROR = "error";
    private static final String PARAM_SUCCESS = "success";

    public static String loadUserDataToSession(HttpServletRequest request, String login) {
        UserDao userDao = MysqlDaoFactory.getInstance().getUserDao();
        User user = userDao.findByUsername(login);
        request.getSession().setAttribute(SESSION_USER, user);

        loadSubscriptionData(request, user);
        loadTransactionData(request, user);

        return Pages.getInstance().get(Pages.MAIN);
    }

    public static void loadSubscriptionData(HttpServletRequest request, User user) {
        SubscriptionDao subscriptionDao = MysqlDaoFactory.getInstance().getSubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);
    }

    public static void loadTransactionData(HttpServletRequest request, User user) {
        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        List<Transaction> transactions = transactionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_TRANSACTIONS, transactions);
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
