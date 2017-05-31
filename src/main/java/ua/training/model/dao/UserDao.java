package ua.training.model.dao;

import ua.training.model.entities.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(User user);
}
