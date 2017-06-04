package ua.training.model.dao.mysql;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.SubscriptionDao;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.model.entities.Subscription;
import ua.training.model.entities.Transaction;
import ua.training.model.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlSubscriptionDao implements SubscriptionDao {
    private final static Logger LOGGER = LogManager.getLogger(MysqlSubscriptionDao.class);

    private final static int COLUMN_USERNAME = 1;
    private final static int COLUMN_EDITION_ID = 2;
    private final static int COLUMN_TRANSACTION = 3;
    private final static int COLUMN_EXPIRATION_DATE = 4;

    private final static MysqlSubscriptionDao SUBSCRIPTION_DAO = new MysqlSubscriptionDao();

    private MysqlSubscriptionDao() {
    }

    static MysqlSubscriptionDao getInstance() {
        return SUBSCRIPTION_DAO;
    }

    @Override
    public List<Subscription> findByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user_subscriptions WHERE users_username = ?");
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public List<Subscription> findByPeriodical(int periodicalId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user_subscriptions WHERE pereodicals_edition_id = ?");
            statement.setInt(1, periodicalId);
            resultSet = statement.executeQuery();
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public List<Subscription> findByTransactionNumber(int transactionId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM user_subscriptions WHERE transactions_transaction_id = ?");
            statement.setInt(1, transactionId);
            resultSet = statement.executeQuery();
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public boolean insert(Subscription subscription) {
        return updateSubscriptionByQuery(subscription,
                "INSERT INTO user_subscriptions VALUES (?, ?,?,?)");
    }

    @Override
    public boolean update(Subscription subscription) {
        return updateSubscriptionByQuery(subscription,
                new StringBuilder(100)
                        .append("UPDATE user_subscriptions SET ")
                        .append("users_username = ?, ")
                        .append("periodicals_edition_id = ?, ")
                        .append("transactions_transaction_id = ?, ")
                        .append("expiration_date = ?")
                        .toString());
    }

    private List<Subscription> resultToList(ResultSet resultSet) throws SQLException {
        List<Subscription> list = new ArrayList<>();
        while (resultSet.next()) {
            Subscription subscription = createSubscriptionFromResult(resultSet);
            list.add(subscription);
        }
        resultSet.close();
        return list;
    }

    private Subscription createSubscriptionFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        String username = resultSet.getString(COLUMN_USERNAME);
        User user = MysqlUserDao.getInstance().findByUsername(username);

        int editionId = resultSet.getInt(COLUMN_EDITION_ID);
        PeriodicalEdition edition = MysqlPeriodicalDao.getInstance().findById(editionId);

        int transactionId = resultSet.getInt(COLUMN_TRANSACTION);
        Transaction transaction = MysqlTransactionDao.getInstance().findById(transactionId);

        Timestamp expirationDate = resultSet.getTimestamp(COLUMN_EXPIRATION_DATE);
        return new Subscription(user, edition, transaction, expirationDate);
    }

    private boolean updateSubscriptionByQuery(Subscription subscription, String query) {
        User user = subscription.getUser();
        PeriodicalEdition pe = subscription.getEdition();
        Transaction ta = subscription.getTransaction();
        Timestamp expDate = subscription.getExpirationDate();

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(COLUMN_USERNAME, user.getUsername());
            statement.setInt(COLUMN_EDITION_ID, pe.getEditionId());
            statement.setInt(COLUMN_TRANSACTION, ta.getTransactionId());
            statement.setTimestamp(COLUMN_EXPIRATION_DATE, expDate);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }
}
