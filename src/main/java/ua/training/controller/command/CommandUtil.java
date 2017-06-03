package ua.training.controller.command;

import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.Transaction;
import ua.training.model.entities.User;
import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CommandUtil {
    private static final String SESSION_USER = "user";
    private static final String SESSION_PERIODICALS = "userPeriodicals";
    private static final String SESSION_TRANSACTIONS = "userTransactions";

    private static final String PARAM_ERROR = "error";
    private static final String PARAM_SUCCESS = "success";

    public static String loadUserDataToSession(HttpServletRequest request, String login) {
        UserDao userDao = MysqlDaoFactory.getInstance().getUserDao();
        User user = userDao.findByUsername(login);
        request.getSession().setAttribute(SESSION_USER, user);

        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        List<Transaction> transactions = transactionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_TRANSACTIONS, transactions);

        SubscriptionDao subscriptionDao = MysqlDaoFactory.getInstance().getSubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);

        return  Config.getInstance().getProperty(Config.MAIN);
    }

    public static String userError(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_ERROR, Message.getProperty(message));
        return Config.getInstance().getProperty(redirectPage);
    }

    public static String userSuccess(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_SUCCESS, Message.getProperty(message));
        return Config.getInstance().getProperty(redirectPage);
    }
}
