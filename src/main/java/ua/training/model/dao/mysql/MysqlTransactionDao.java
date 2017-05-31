package ua.training.model.dao.mysql;

import org.apache.logging.log4j.*;
import ua.training.model.dao.TransactionDao;
import ua.training.model.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlTransactionDao implements TransactionDao {
    private static final Logger LOGGER = LogManager.getLogger(MysqlTransactionDao.class);

    private final static int COLUMN_ID = 1;
    private final static int COLUMN_USER = 2;
    private final static int COLUMN_TIME = 3;
    private final static int COLUMN_TOTAL_PRICE = 4;

    private final static MysqlTransactionDao TRANSACTION_DAO = new MysqlTransactionDao();

    private Connection connection;
    private PreparedStatement statement;

    private MysqlTransactionDao() {
    }

    static MysqlTransactionDao getInstance() {
        return TRANSACTION_DAO;
    }

    @Override
    public List<Transaction> findAll() {
        Statement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM transactions");
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public List<Transaction> findByUsername(String username) {
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE transactions_username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultToList(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public Transaction findById(int id) {
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM transactions WHERE transaction_id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return createTransactionFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return null;
    }

    @Override
    public boolean insert(Transaction transaction) {
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    " INSERT INTO transactions (transactions_username, transaction_time, totall_price) VALUES (?,?,?)");
            statement.setInt(COLUMN_ID, transaction.getTransactionId());
            statement.setString(COLUMN_USER, transaction.getUser().getUsername());
            statement.setTimestamp(COLUMN_TIME, transaction.getTransactionTime());
            statement.setBigDecimal(COLUMN_TOTAL_PRICE, transaction.getTotalPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    private List<Transaction> resultToList(ResultSet resultSet) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        while (resultSet.next()) {
            Transaction transaction = createTransactionFromResult(resultSet);
            list.add(transaction);
        }
        return list;
    }

    private Transaction createTransactionFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        int transactionId = resultSet.getInt(COLUMN_ID);
        String username = resultSet.getString(COLUMN_USER);
        Timestamp date = resultSet.getTimestamp(COLUMN_TIME);
        BigDecimal totalPrice = resultSet.getBigDecimal(COLUMN_TOTAL_PRICE);
        User user = MysqlUserDao.getInstance().findByUsername(username);
        resultSet.close();
        return new Transaction(transactionId, user, date, totalPrice);
    }
}
