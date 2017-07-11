package ua.training.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.model.repository.UserRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.UserDTO;
import ua.training.model.dto.UserGroup;
import ua.training.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * UserService is responsible for creating User DTO from data passed by command. Also hashes password.
 * Has no state, so declared as Singleton.
 * @author Oleksandr Mukhopad
 */
@Service
public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserRepository REPOSITORY = MysqlRepositoryFactory.getInstance().getUserRepository();
    private static final UserService SERVICE = new UserService();

    private UserService() {}

    public static UserService getService() {
        return SERVICE;
    }

    /**
     * Takes login and password from command, looks for user in database by his username and hashes his password.
     * Method return true when user exists and his stored password hash equals entered hashed value.
     * @param login user's name
     * @param password user's string password
     * @return false if user wasn't found or if password hashes don't match, true otherwise
     */
    public boolean login(String login, String password) {
        LOGGER.info(Log.USER_LOGIN);
        password = hashPassword(password);
        UserDTO user = REPOSITORY.findByUsername(login);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Takes obtained from user login, email and password,
     * @param login user login
     * @param password user's string password
     * @param email user's email
     * @return true if user was successfully inserted into database, false otherwise
     */
    public boolean register(String login, String password, String email) {
        LOGGER.info(Log.USER_REGISTER);
        password = hashPassword(password);
        UserDTO user = new UserDTO(passwordSafeInput(login), password, email, UserGroup.USER);
        return REPOSITORY.insert(user);
    }

    /**
     * Simple hashing method that uses symmetrically SHA-256 encryption
     * @param password user password
     * @return hashed password in string
     */
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

    /**
     * Removes all non-latin symbols, all special symbols except "-" and "_", spaces, tabs and html tags.
     * @param password user's string password
     * @return new String without invalid symbols in lower case.
     */
    String passwordSafeInput(String password) {
        return password.replaceAll("<[^>]*>|[\\s]|[^\\w-]", "").toLowerCase();
    }
}
