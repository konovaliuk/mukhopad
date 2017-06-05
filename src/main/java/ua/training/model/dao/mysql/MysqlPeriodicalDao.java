package ua.training.model.dao.mysql;

import org.apache.logging.log4j.*;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.entities.PeriodicalEdition;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class MysqlPeriodicalDao implements PeriodicalDao {
    private final static Logger LOGGER = LogManager.getLogger(MysqlPeriodicalDao.class);

    private final static int COLUMN_ID = 1;
    private final static int COLUMN_NAME = 2;
    private final static int COLUMN_PRICE = 3;

    private static MysqlPeriodicalDao PERIODICALS_DAO = new MysqlPeriodicalDao();

    private MysqlPeriodicalDao() {
    }

    static MysqlPeriodicalDao getInstance() {
        return PERIODICALS_DAO;
    }

    @Override
    public List<PeriodicalEdition> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM periodical_editions");
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
    public PeriodicalEdition findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM periodical_editions WHERE edition_name = ?");
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            return createPeriodicalFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public PeriodicalEdition findById(int editionId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "SELECT * FROM periodical_editions WHERE edition_id = ?");
            statement.setInt(1, editionId);
            resultSet = statement.executeQuery();
            return createPeriodicalFromResult(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement, resultSet);
        }
        return null;
    }

    @Override
    public boolean insert(PeriodicalEdition pe) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "INSERT INTO periodical_editions (edition_name, subscription_price) VALUES (?,?)");
            statement.setString(COLUMN_NAME-1, pe.getEditionName());
            statement.setBigDecimal(COLUMN_PRICE-1, pe.getEditionPrice());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean update(PeriodicalEdition pe) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    "UPDATE periodical_editions SET edition_name = ?, subscription_price = ? WHERE edition_id = ?");
            statement.setString(COLUMN_NAME-1, pe.getEditionName());
            statement.setBigDecimal(COLUMN_PRICE-1, pe.getEditionPrice());
            statement.setInt(COLUMN_ID+2, pe.getEditionId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            MysqlDatasource.close(connection, statement);
        }
        return false;
    }

    @Override
    public boolean delete(int editionId) {
        Connection connection;
        PreparedStatement statement;
        try {
            connection = MysqlDatasource.getConnection();
            statement = connection.prepareStatement(
                    " DELETE FROM periodical_editions WHERE edition_id = ?");
            statement.setInt(1, editionId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private List<PeriodicalEdition> resultToList(ResultSet resultSet) throws SQLException {
        List<PeriodicalEdition> list = new ArrayList<>();
        while (resultSet.next()) {
            PeriodicalEdition subscription = createPeriodicalFromResult(resultSet);
            list.add(subscription);
        }
        return list;
    }

    private PeriodicalEdition createPeriodicalFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        int editionId = resultSet.getInt(COLUMN_ID);
        String editionName = resultSet.getString(COLUMN_NAME);
        BigDecimal editionPrice = resultSet.getBigDecimal(COLUMN_PRICE);
        return new PeriodicalEdition(editionId, editionName, editionPrice);
    }
}
