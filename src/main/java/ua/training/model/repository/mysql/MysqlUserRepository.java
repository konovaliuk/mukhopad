package ua.training.model.repository.mysql;

import org.apache.logging.log4j.*;
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

    @Override
    public List<UserDTO> findAll() {
        Connection connection = null;
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(BASE_SQL_USER_QUERY);
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public UserDTO findByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    BASE_SQL_USER_QUERY + "AND username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return createUserFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public UserDTO findByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    BASE_SQL_USER_QUERY + "AND email = ?");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            return createUserFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public boolean insert(UserDTO user) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO users VALUES (?,?,?,?)");
            statement.setString(COLUMN_USERNAME, user.getUsername());
            statement.setString(COLUMN_PASSWORD, user.getPassword());
            statement.setString(COLUMN_EMAIL, user.getEmail());
            String groupName = user.getGroup().getGroupName();
            statement.setInt(COLUMN_USER_GROUP_ID, UserGroup.valueOf(groupName).ordinal());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean update(UserDTO user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, email = ?, user_group_id = ?");
            statement.setString(COLUMN_USERNAME, user.getUsername());
            statement.setString(COLUMN_PASSWORD, user.getPassword());
            statement.setString(COLUMN_EMAIL, user.getEmail());
            String groupName = user.getGroup().getGroupName();
            statement.setInt(COLUMN_USER_GROUP_ID, UserGroup.valueOf(groupName).ordinal());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean delete(UserDTO user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "DELETE FROM users WHERE username = ?");
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
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
        String groupName = resultSet.getString(4);

        UserGroup group = UserGroup.valueOf(groupName.toUpperCase());
        return new UserDTO(username, password, email, group);
    }
}
