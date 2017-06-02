package ua.training.model.services;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.dao.TransactionDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.Transaction;
import ua.training.model.entities.User;
import ua.training.model.entities.UserGroup;
import ua.training.util.Config;
import ua.training.util.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserDao DAO = MysqlDaoFactory.getInstance().getUserDao();
    private static UserService instance;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String EMAIL = "email";

    private static final String SESSION_USER = "user";
    private static final String SESSION_PERIODICALS = "userPeriodicals";
    private static final String SESSION_TRANSACTIONS = "userTransactions";

    private static final String PARAM_ERROR = "error";
    private static final String PARAM_SUCCESS = "success";

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public String login(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        password = hashPassword(password);

        User user = DAO.findByUsername(login);

        String page;
        if (user != null && user.getPassword().equals(password)) {
            return loadUserDataToSession(request, user);
        } else {
            return userError(request, Message.LOGIN_ERROR, Config.LOGIN);
        }
    }

    public String register(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        if (!password.equals(request.getParameter(CONFIRM_PASSWORD))) {
            return userError(request, Message.PASSWORD_MISMATCH_ERROR, Config.REGISTRATION);
        }
        password = hashPassword(password);

        String email = request.getParameter(EMAIL);
        User user = new User(login, password, email, UserGroup.USER);

        String page;
        if (DAO.insert(user)) {
            return loadUserDataToSession(request, user);
        } else {
            return userError(request, Message.REGISTRATION_ERROR, Config.REGISTRATION);
        }
    }

    public String loadUserDataToSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute(SESSION_USER, user);

        TransactionDao transactionDao = MysqlDaoFactory.getInstance().getTransactionDao();
        List<Transaction> transactions = transactionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_TRANSACTIONS, transactions);

        SubscriptionDao subscriptionDao = MysqlDaoFactory.getInstance().getSubscriptionDao();
        List<Subscription> subscriptions = subscriptionDao.findByUsername(user.getUsername());
        request.getSession().setAttribute(SESSION_PERIODICALS, subscriptions);

        return Config.getInstance().getProperty(Config.MAIN);
    }

    public String userError(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_ERROR, Message.getProperty(message));
        return Config.getInstance().getProperty(redirectPage);
    }

    public String userSuccess(HttpServletRequest request, String message, String redirectPage) {
        request.setAttribute(PARAM_SUCCESS, Message.getProperty(message));
        return Config.getInstance().getProperty(redirectPage);
    }

    String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        return hashedPassword;
    }
}
