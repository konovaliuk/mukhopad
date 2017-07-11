package ua.training.model.repository.mysql;

import org.apache.logging.log4j.*;
import ua.training.model.repository.SubscriptionRepository;
import ua.training.model.dto.*;

import java.sql.*;
import java.util.*;

public class MysqlSubscriptionRepository implements SubscriptionRepository {
    private final static Logger LOGGER = LogManager.getLogger(MysqlSubscriptionRepository.class);

    private final static int COLUMN_USERNAME = 1;
    private final static int COLUMN_EDITION_ID = 2;
    private final static int COLUMN_TRANSACTION = 3;
    private final static int COLUMN_EXPIRATION_DATE = 4;

    private final static MysqlSubscriptionRepository SUBSCRIPTION_REPOSITORY = new MysqlSubscriptionRepository();

    private MysqlSubscriptionRepository() {
    }

    static MysqlSubscriptionRepository getInstance() {
        return SUBSCRIPTION_REPOSITORY;
    }

    @Override
    public List<SubscriptionDTO> findByUsername(String username) {
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
    public List<SubscriptionDTO> findByPeriodical(int periodicalId) {
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
    public List<SubscriptionDTO> findByTransactionNumber(int transactionId) {
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
    public boolean insert(SubscriptionDTO subscription) {
        return updateSubscriptionByQuery(subscription,
                "INSERT INTO user_subscriptions VALUES (?, ?,?,?)");
    }

    @Override
    public boolean update(SubscriptionDTO subscription) {
        return updateSubscriptionByQuery(subscription,
                new StringBuilder(100)
                        .append("UPDATE user_subscriptions SET ")
                        .append("users_username = ?, ")
                        .append("periodicals_edition_id = ?, ")
                        .append("transactions_transaction_id = ?, ")
                        .append("expiration_date = ?")
                        .toString());
    }

    private List<SubscriptionDTO> resultToList(ResultSet resultSet) throws SQLException {
        List<SubscriptionDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            SubscriptionDTO subscription = createSubscriptionFromResult(resultSet);
            list.add(subscription);
        }
        resultSet.close();
        return list;
    }

    private SubscriptionDTO createSubscriptionFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        String username = resultSet.getString(COLUMN_USERNAME);
        UserDTO user = MysqlUserRepository.getInstance().findByUsername(username);

        int editionId = resultSet.getInt(COLUMN_EDITION_ID);
        PeriodicalEditionDTO edition = MysqlPeriodicalRepository.getInstance().findById(editionId);

        int transactionId = resultSet.getInt(COLUMN_TRANSACTION);
        TransactionDTO transaction = MysqlTransactionRepository.getInstance().findById(transactionId);

        Timestamp expirationDate = resultSet.getTimestamp(COLUMN_EXPIRATION_DATE);
        return new SubscriptionDTO(user, edition, transaction, expirationDate);
    }

    private boolean updateSubscriptionByQuery(SubscriptionDTO subscription, String query) {
        UserDTO user = subscription.getUser();
        PeriodicalEditionDTO pe = subscription.getEdition();
        TransactionDTO ta = subscription.getTransaction();
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
