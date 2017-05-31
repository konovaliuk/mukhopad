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

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    public String login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute(LOGIN);
        String password = (String) session.getAttribute(PASSWORD);
        password = hashPassword(password);

        User user = DAO.findByUsername(login);

        String page;
        if (user.getPassword() == password) {
            request.setAttribute("user", login);
            page = Config.getInstance().getProperty(Config.MAIN);
        } else {
            request.setAttribute("error", Message.getInstance().getProperty(Message.LOGIN_ERROR));
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        return page;
    }

    public String register(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String login = (String) session.getAttribute(LOGIN);
        User user = DAO.findByUsername(login);

        if(user != null) {
            return Config.getInstance().getProperty(Config.ERROR);
        }

        String password = (String) session.getAttribute(PASSWORD);
        password = hashPassword(password);

        String email = (String) session.getAttribute(EMAIL);
        user = new User(login, password, email, UserGroup.USER);

        String page;
        if (DAO.insert(user)) {
            request.setAttribute("user", login);
            page = Config.getInstance().getProperty(Config.MAIN);
        } else {
            request.setAttribute("error", Message.getInstance().getProperty(Message.REGISTRATION_ERROR));
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        return page;
    }

    String hashPassword(String password) {
        String hashedPassword = null;
        try {
            // Create MessageDigest instance for MD5
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
