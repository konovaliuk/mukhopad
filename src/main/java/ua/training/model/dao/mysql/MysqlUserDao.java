package ua.training.model.dao.mysql;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.UserDao;
import ua.training.model.entities.User;
import ua.training.model.entities.UserGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUserDao implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(MysqlUserDao.class);

    private final static int COLUMN_USERNAME = 1;
    private final static int COLUMN_PASSWORD = 2;
    private final static int COLUMN_EMAIL = 3;
    private final static int COLUMN_USER_GROUP_ID = 4;
    private final static int COLUMN_USER_GROUP_NAME = 5;

    private final static String BASE_SQL_USER_QUERY
            = "SELECT users.username, users.password, users.email, groups.group_name FROM users, groups WHERE users.user_group_id = groups.group_id ";

    private final static MysqlUserDao USER_DAO = new MysqlUserDao();

    private Connection connection;
    private PreparedStatement statement;

    private MysqlUserDao() {
    }

    static MysqlUserDao getInstance() {
        return USER_DAO;
    }

    @Override
    public List<User> findAll() {
        Statement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(BASE_SQL_USER_QUERY);
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    BASE_SQL_USER_QUERY + "AND username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return createUserFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    BASE_SQL_USER_QUERY + "AND email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return createUserFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
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
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean update(User user) {
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
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "DELETE FROM users WHERE username = ?");
            statement.setString(1, user.getUsername());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    private List<User> resultToList(ResultSet resultSet) throws SQLException {
        List<User> list = new ArrayList<>();
        while (resultSet.next()) {
            User user = createUserFromResult(resultSet);
            list.add(user);
        }
        return list;
    }

    private User createUserFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        String username = resultSet.getString(COLUMN_USERNAME);
        String password = resultSet.getString(COLUMN_PASSWORD);
        String email = resultSet.getString(COLUMN_EMAIL);
        String groupName = resultSet.getString(4);

        UserGroup group = UserGroup.valueOf(groupName.toUpperCase());

        resultSet.close();
        return new User(username, password, email, group);
    }
}
