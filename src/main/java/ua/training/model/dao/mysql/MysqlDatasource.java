package ua.training.model.dao.mysql;

import org.apache.logging.log4j.*;

import javax.naming.*;
import javax.sql.DataSource;
import java.sql.*;

class MysqlDatasource {
    private static final Logger LOGGER = LogManager.getLogger(MysqlDatasource.class);

    static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(
                    "java:comp/env/jdbc/Periodicals");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            LOGGER.error(e.getMessage());
        }
        return connection;
    }

    static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(connection, statement);
        try {
            if (resultSet != null) {resultSet.close();}
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    static void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {statement.close();}
            if (connection != null) {connection.close();}
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}

