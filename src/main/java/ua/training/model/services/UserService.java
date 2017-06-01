package ua.training.model.services;

import org.apache.logging.log4j.*;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.*;
import ua.training.util.*;

import javax.servlet.http.*;
import java.security.*;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserDao DAO = MysqlDaoFactory.getInstance().getUserDao();
    private static UserService instance;

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";
    private static final String EMAIL = "email";

    private static final String PARAM_USER = "user";
    private static final String PARAM_ERROR = "error";

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
            request.getSession().setAttribute(PARAM_USER, login);
            page = Config.getInstance().getProperty(Config.MAIN);
        } else {
            request.setAttribute(PARAM_ERROR, Message.getInstance().getProperty(Message.LOGIN_ERROR));
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
        return page;
    }

    public String register(HttpServletRequest request, HttpServletResponse response) {
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        if (!password.equals(request.getParameter(CONFIRM_PASSWORD))) {
            request.setAttribute(PARAM_ERROR, Message.getInstance().getProperty(Message.PASSWORD_MISMATCH_ERROR));
            return Config.getInstance().getProperty(Config.REGISTRATION);
        }
        password = hashPassword(password);

        String email = request.getParameter(EMAIL);
        User user = new User(login, password, email, UserGroup.USER);

        String page;
        if (DAO.insert(user)) {
            request.getSession().setAttribute(PARAM_USER, login);
            page = Config.getInstance().getProperty(Config.MAIN);
        } else {
            request.setAttribute(PARAM_ERROR, Message.getInstance().getProperty(Message.REGISTRATION_ERROR));
            page = Config.getInstance().getProperty(Config.REGISTRATION);
        }
        return page;
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
