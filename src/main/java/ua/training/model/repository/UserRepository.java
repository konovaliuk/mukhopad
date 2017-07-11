package ua.training.model.repository;

import ua.training.model.dto.UserDTO;

import java.util.List;

public interface UserRepository {
    /**
     * Finds all Users in database.
     * @return user
     */
    List<UserDTO> findAll();

    /**
     * Finds certain User by its unique username.
     * @param username user's name
     * @return user
     */
    UserDTO findByUsername(String username);

    /**
     * Finds certain User by its unique email.
     * @param email user email
     * @return user
     */
    UserDTO findByEmail(String email);

    boolean insert(UserDTO user);

    boolean update(UserDTO user);

    boolean delete(UserDTO user);
}
