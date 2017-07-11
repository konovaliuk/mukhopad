package ua.training.model.dao.mysql;

import org.apache.logging.log4j.*;
import ua.training.model.dao.TransactionDao;
import ua.training.model.entities.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class MysqlTransactionDao implements TransactionDao {
    private static final Logger LOGGER = LogManager.getLogger(MysqlTransactionDao.class);

    private final static int COLUMN_ID = 1;
    private final static int COLUMN_USER = 2;
    private final static int COLUMN_TIME = 3;
    private final static int COLUMN_TOTAL_PRICE = 4;

    private final static MysqlTransactionDao TRANSACTION_DAO = new MysqlTransactionDao();

    private MysqlTransactionDao() {
    }

    static MysqlTransactionDao getInstance() {
        return TRANSACTION_DAO;
    }

    @Override
    public List<Transaction> findAll() {
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
    public List<Transaction> findByUsername(String username) {
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
    public Transaction findById(int id) {
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
    public boolean insert(Transaction transaction) {
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

    private List<Transaction> resultToList(ResultSet resultSet) throws SQLException {
        List<Transaction> list = new ArrayList<>();
        while (resultSet.next()) {
            Transaction transaction = createTransactionFromResult(resultSet);
            list.add(transaction);
        }
        resultSet.close();
        return list;
    }

    private Transaction createTransactionFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        int transactionId = resultSet.getInt(COLUMN_ID);
        String username = resultSet.getString(COLUMN_USER);
        Timestamp date = resultSet.getTimestamp(COLUMN_TIME);
        BigDecimal totalPrice = resultSet.getBigDecimal(COLUMN_TOTAL_PRICE);
        User user = MysqlUserDao.getInstance().findByUsername(username);
        return new Transaction(transactionId, user, date, totalPrice);
    }
}
