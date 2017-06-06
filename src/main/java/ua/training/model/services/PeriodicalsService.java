package ua.training.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.util.Log;

import java.math.BigDecimal;

/**
 * Accepts controller fetched input data, warp it into PeriodicalEdition object and pass to DAO.
 * @author Oleksandr Mukhopad
 */
public class PeriodicalsService {
    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsService.class);
    private static final PeriodicalDao DAO = MysqlDaoFactory.getInstance().getPeriodicalDao();
    private static final PeriodicalsService SERVICE = new PeriodicalsService();

    private PeriodicalsService() {}

    public static PeriodicalsService getService() {
        return SERVICE;
    }

    /**
     * Takes name and price of edition, warps it into DTO and passes to DAO for insertion.
     * @param name name of periodical
     * @param price periodical price
     * @return true if periodical was inserted successfully, false otherwise
     */
    public boolean addPeriodical(String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_ADD + name);
        return DAO.insert(new PeriodicalEdition(0, safeInput(name), price));
    }

    /**
     * Takes and id, existing edition, new name and price,
     * warps it into DTO and passes to DAO for update.
     * @param id periodical unique id
     * @param name name of periodical
     * @param price periodical price
     * @return true if periodical was updated successfully, false otherwise
     */
    public boolean updatePeriodical(int id, String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_UPDATE + name);
        return DAO.update(new PeriodicalEdition(id, safeInput(name), price));
    }

    /**
     * Takes an id of existing periodical and passess it to DAO for deletion.
     * @param id periodical unique id
     * @return true if periodical was inserted successfully, false otherwise
     */
    public boolean deletePeriodical(int id) {
        LOGGER.info(Log.PERIODICAL_DELETE + id);
        return DAO.delete(id);
    }

    /**
     * Removes all html tags and special symbols from name
     * except spaces, dashes and underscores.
     * @param periodicalName inputted name of periodical
     * @return new string without special symbols.
     */
    String safeInput(String periodicalName) {
        return periodicalName.replaceAll("<[^>]*>|[@#$%^&*;/|<>\"']+", "");
    }
}
