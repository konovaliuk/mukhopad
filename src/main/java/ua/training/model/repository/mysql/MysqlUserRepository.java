package ua.training.model.repository.mysql;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.training.model.repository.UserRepository;
import ua.training.model.dto.*;

import java.sql.*;
import java.util.*;

@Repository
public class MysqlUserRepository implements UserRepository {
    private static final Logger LOGGER = LogManager.getLogger(MysqlUserRepository.class);

    private final static int COLUMN_USERNAME = 1;
    private final static int COLUMN_PASSWORD = 2;
    private final static int COLUMN_EMAIL = 3;
    private final static int COLUMN_USER_GROUP_ID = 4;

    private final static String BASE_SQL_USER_QUERY
            = "SELECT users.username, users.password, users.email, groups.group_name FROM users, groups WHERE users.user_group_id = groups.group_id ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserDTO> findAll() {
        return jdbcTemplate.query(BASE_SQL_USER_QUERY, this::resultToList);
    }

    @Override
    public UserDTO findByUsername(String username) {
        String query = BASE_SQL_USER_QUERY + "AND username = ?";
        return jdbcTemplate.query(query, this::createUserFromResult);
    }

    @Override
    public UserDTO findByEmail(String email) {
        String query = BASE_SQL_USER_QUERY + "AND email = ?";
        return jdbcTemplate.query(query, this::createUserFromResult);
    }

    @Override
    public boolean insert(UserDTO user) {
        try {
            String query = "INSERT INTO users VALUES (?,?,?,?)";
            jdbcTemplate.update(
                    query,
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    UserGroup.valueOf(user.getGroup().getGroupName()).ordinal()
            );
            return true;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(UserDTO user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            String query = "UPDATE users SET username = ?, password = ?, email = ?, user_group_id = ?";
            jdbcTemplate.update(
                    query,
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    UserGroup.valueOf(user.getGroup().getGroupName()).ordinal()
            );
            return true;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(UserDTO user) {

        try {
            jdbcTemplate.update("DELETE FROM users WHERE username = ?" + user);
            return true;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private List<UserDTO> resultToList(ResultSet resultSet) throws SQLException {
        List<UserDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            UserDTO user = createUserFromResult(resultSet);
            list.add(user);
        }
        return list;
    }

    private UserDTO createUserFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        String username = resultSet.getString(COLUMN_USERNAME);
        String password = resultSet.getString(COLUMN_PASSWORD);
        String email = resultSet.getString(COLUMN_EMAIL);
        String groupName = resultSet.getString(COLUMN_USER_GROUP_ID);

        UserGroup group = UserGroup.valueOf(groupName.toUpperCase());
        return new UserDTO(username, password, email, group);
    }
}
