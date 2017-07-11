package ua.training.model.repository.mysql;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private UserRepository userRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MysqlTransactionRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<TransactionDTO> findAll() {
        String query = "SELECT * FROM transactions ORDER BY transaction_Id DESC";
        return jdbcTemplate.query(query, this::resultToList);
    }

    @Override
    public List<TransactionDTO> findByUsername(String username) {
        String query = "SELECT * FROM transactions WHERE transactions_username = ?" + username;
        return jdbcTemplate.query(query, this::resultToList);
    }

    @Override
    public TransactionDTO findById(int id) {
        String query = "SELECT * FROM transactions WHERE transaction_id = ?";
        return jdbcTemplate.query(query, this::createTransactionFromResult);
    }

    @Override
    public boolean insert(TransactionDTO transaction) {

        try {
            jdbcTemplate.update(
                    " INSERT INTO transactions (transactions_username, transaction_time, totall_price) VALUES (?,?,?)",
                    transaction.getUser().getUsername(),
                    transaction.getTransactionTime(),
                    transaction.getTotalPrice()
            );
            return true;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    public int tableSize() {
        return findAll().size();
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
