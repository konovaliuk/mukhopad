package ua.training.model.repository.mysql;

import org.apache.logging.log4j.*;
import org.springframework.stereotype.Repository;
import ua.training.model.repository.TransactionRepository;
import ua.training.model.dto.*;
import ua.training.model.repository.UserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

@Repository
public class MysqlTransactionRepository implements TransactionRepository {
    private static final Logger LOGGER = LogManager.getLogger(MysqlTransactionRepository.class);

    private final static int COLUMN_ID = 1;
    private final static int COLUMN_USER = 2;
    private final static int COLUMN_TIME = 3;
    private final static int COLUMN_TOTAL_PRICE = 4;

    UserRepository userRepository;

    MysqlTransactionRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<TransactionDTO> findAll() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM transactions ORDER BY transaction_Id DESC");
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public List<TransactionDTO> findByUsername(String username) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE transactions_username = ?");
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
    public TransactionDTO findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE transaction_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createTransactionFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public boolean insert(TransactionDTO transaction) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    " INSERT INTO transactions (transactions_username, transaction_time, totall_price) VALUES (?,?,?)");
            statement.setString(COLUMN_USER - 1 , transaction.getUser().getUsername());
            statement.setTimestamp(COLUMN_TIME - 1, transaction.getTransactionTime());
            statement.setBigDecimal(COLUMN_TOTAL_PRICE - 1, transaction.getTotalPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    public int tableSize() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT count(*) FROM transactions");
            int size = 0;
            while (resultSet.next()){
                size = resultSet.getInt(1);
            }
            return size;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return 0;
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
    }

    private List<TransactionDTO> resultToList(ResultSet resultSet) throws SQLException {
        List<TransactionDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            TransactionDTO transaction = createTransactionFromResult(resultSet);
            list.add(transaction);
        }
        resultSet.close();
        return list;
    }

    private TransactionDTO createTransactionFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        int transactionId = resultSet.getInt(COLUMN_ID);
        String username = resultSet.getString(COLUMN_USER);
        Timestamp date = resultSet.getTimestamp(COLUMN_TIME);
        BigDecimal totalPrice = resultSet.getBigDecimal(COLUMN_TOTAL_PRICE);
        UserDTO user = userRepository.findByUsername(username);
        return new TransactionDTO(transactionId, user, date, totalPrice);
    }
}
