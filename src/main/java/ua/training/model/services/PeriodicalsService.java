package ua.training.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ua.training.model.repository.PeriodicalRepository;
import ua.training.model.repository.mysql.MysqlRepositoryFactory;
import ua.training.model.dto.PeriodicalEditionDTO;
import ua.training.util.Log;

import java.math.BigDecimal;

/**
 * Accepts controller fetched input data, warp it into PeriodicalEdition object and pass to REPOSITORY.
 * @author Oleksandr Mukhopad
 */
@Service
public class PeriodicalsService {
    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsService.class);
    private static final PeriodicalRepository REPOSITORY = MysqlRepositoryFactory.getInstance().getPeriodicalRepository();
    private static final PeriodicalsService SERVICE = new PeriodicalsService();

    private PeriodicalsService() {}

    public static PeriodicalsService getService() {
        return SERVICE;
    }

    /**
     * Takes name and price of edition, warps it into DTO and passes to REPOSITORY for insertion.
     * @param name name of periodical
     * @param price periodical price
     * @return true if periodical was inserted successfully, false otherwise
     */
    public boolean addPeriodical(String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_ADD + name);
        return REPOSITORY.insert(new PeriodicalEditionDTO(0, safeInput(name), price));
    }

    /**
     * Takes and id, existing edition, new name and price,
     * warps it into DTO and passes to REPOSITORY for update.
     * @param id periodical unique id
     * @param name name of periodical
     * @param price periodical price
     * @return true if periodical was updated successfully, false otherwise
     */
    public boolean updatePeriodical(int id, String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_UPDATE + name);
        return REPOSITORY.update(new PeriodicalEditionDTO(id, safeInput(name), price));
    }

    /**
     * Takes an id of existing periodical and passess it to REPOSITORY for deletion.
     * @param id periodical unique id
     * @return true if periodical was inserted successfully, false otherwise
     */
    public boolean deletePeriodical(int id) {
        LOGGER.info(Log.PERIODICAL_DELETE + id);
        return REPOSITORY.delete(id);
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
