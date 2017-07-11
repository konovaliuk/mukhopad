package ua.training.model.repository.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.model.dto.SubscriptionDTO;
import ua.training.model.dto.TransactionDTO;
import ua.training.model.dto.UserDTO;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.SubscriptionRepository;
import ua.training.model.repository.TransactionRepository;
import ua.training.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MysqlSubscriptionRepository implements SubscriptionRepository {
    private final static Logger LOGGER = LogManager.getLogger(MysqlSubscriptionRepository.class);

    private UserRepository userRepository;
    private PeriodicalRepository periodicalRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    MysqlSubscriptionRepository(UserRepository ur, PeriodicalRepository pr, TransactionRepository tr) {
        this.userRepository = ur;
        this.periodicalRepository = pr;
        this.transactionRepository = tr;
    }

    private final static int COLUMN_USERNAME = 1;
    private final static int COLUMN_EDITION_ID = 2;
    private final static int COLUMN_TRANSACTION = 3;
    private final static int COLUMN_EXPIRATION_DATE = 4;

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
    public List<SubscriptionDTO> findByTransactionId(int transactionId) {
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
                "UPDATE user_subscriptions SET users_username = ?, periodicals_edition_id = ?, transactions_transaction_id = ?, expiration_date = ?");
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
        UserDTO user = userRepository.findByUsername(username);

        int editionId = resultSet.getInt(COLUMN_EDITION_ID);
        PeriodicalEditionDTO edition = periodicalRepository.findById(editionId);

        int transactionId = resultSet.getInt(COLUMN_TRANSACTION);
        TransactionDTO transaction = transactionRepository.findById(transactionId);

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
