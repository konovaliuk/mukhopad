package ua.training.model.dao.mysql;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            LOGGER.log(Level.ERROR, e.getMessage());
        }
        return connection;
    }

    static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(connection, statement);
        try {
            if (resultSet != null) {resultSet.close();}
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }

    static void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {statement.close();}
            if (connection != null) {connection.close();}
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }
}

