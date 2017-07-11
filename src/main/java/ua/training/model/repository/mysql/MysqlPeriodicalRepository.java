package ua.training.model.repository.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.model.repository.PeriodicalRepository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MysqlPeriodicalRepository implements PeriodicalRepository {
    private final static Logger LOGGER = LogManager.getLogger(MysqlPeriodicalRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PeriodicalEditionDTO> findAll() {
        String query = "SELECT * FROM periodical_editions";
        return jdbcTemplate.query(query, this::resultToList);

    }

    @Override
    public PeriodicalEditionDTO findByName(String name) {
        String query = "SELECT * FROM periodical_editions WHERE edition_name = " + name;
        return jdbcTemplate.query(query, this::createPeriodicalFromResult);

    }

    @Override
    public PeriodicalEditionDTO findById(int id) {
        String query = "SELECT * FROM periodical_editions WHERE edition_name = " + id;
        return jdbcTemplate.query(query, this::createPeriodicalFromResult);

    }

    @Override
    public boolean insert(PeriodicalEditionDTO pe) {
        try {
            jdbcTemplate.update(
                    "INSERT INTO periodical_editions (edition_name, subscription_price) VALUES (?,?)",
                    pe.getEditionName(),
                    pe.getEditionPrice()
            );
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(PeriodicalEditionDTO pe) {
        try {
            jdbcTemplate.update(
                    "UPDATE periodical_editions SET edition_name = ?, subscription_price = ? WHERE edition_id = ?",
                    pe.getEditionName(),
                    pe.getEditionPrice(),
                    pe.getEditionId()
            );
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int editionId) {
        try {
            jdbcTemplate.update(" DELETE FROM periodical_editions WHERE edition_id = ?", editionId);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    private List<PeriodicalEditionDTO> resultToList(ResultSet resultSet) throws SQLException {
        List<PeriodicalEditionDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            PeriodicalEditionDTO subscription = createPeriodicalFromResult(resultSet);
            list.add(subscription);
        }
        return list;
    }

    private PeriodicalEditionDTO createPeriodicalFromResult(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) resultSet.next();

        int editionId = resultSet.getInt(1);
        String editionName = resultSet.getString(2);
        BigDecimal editionPrice = resultSet.getBigDecimal(3);
        return new PeriodicalEditionDTO(editionId, editionName, editionPrice);
    }
}
