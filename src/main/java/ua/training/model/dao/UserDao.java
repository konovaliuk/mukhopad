package ua.training.model.dao;

import ua.training.model.entities.User;

import java.util.List;

public interface UserDao {
    /**
     * Finds all Users in database.
     * @return user
     */
    List<User> findAll();

    /**
     * Finds certain User by its unique username.
     * @param username user's name
     * @return user
     */
    User findByUsername(String username);

    /**
     * Finds certain User by its unique email.
     * @param email user email
     * @return user
     */
    User findByEmail(String email);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(User user);
}
