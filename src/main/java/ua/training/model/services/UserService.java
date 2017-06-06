package ua.training.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.User;
import ua.training.model.entities.UserGroup;
import ua.training.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserDao DAO = MysqlDaoFactory.getInstance().getUserDao();
    private static final UserService SERVICE = new UserService();

    private UserService() {}

    public static UserService getService() {
        return SERVICE;
    }

    public boolean login(String login, String password) {
        LOGGER.info(Log.USER_LOGIN);
        password = hashPassword(password);
        User user = DAO.findByUsername(login);
        return user != null && user.getPassword().equals(password);
    }

    public boolean register(String login, String password, String email) {
        LOGGER.info(Log.USER_REGISTER);
        password = hashPassword(password);
        User user = new User(safeInput(login), password, email, UserGroup.USER);
        return DAO.insert(user);
    }

    String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.info(Log.PASSWORD_HASHED);
        return hashedPassword;
    }

    public String safeInput(String s) {
        return s.replaceAll("<[^>]*>|[\\s]|[^\\w-]", "");
    }
}
