package ua.training.model.services;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.User;
import ua.training.model.entities.UserGroup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public boolean login(String login, String password) {
        User user = DAO.findByUsername(login);
        return user != null && user.getPassword().equals(password);
    }

    public boolean register(String login, String password, String email) {
        User user = new User(login, password, email, UserGroup.USER);
        return DAO.insert(user);
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
