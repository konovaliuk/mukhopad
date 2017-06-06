package ua.training.model.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;
import ua.training.util.Log;

import java.math.BigDecimal;

public class PeriodicalsService {
    private static final Logger LOGGER = LogManager.getLogger(PeriodicalsService.class);
    private static final PeriodicalDao DAO = MysqlDaoFactory.getInstance().getPeriodicalDao();
    private static final PeriodicalsService SERVICE = new PeriodicalsService();

    private PeriodicalsService() {}

    public static PeriodicalsService getService() {
        return SERVICE;
    }

    public boolean addPeriodical(String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_ADD + name);
        return DAO.insert(new PeriodicalEdition(0, safeInput(name), price));
    }

    public boolean updatePeriodical(int id, String name, BigDecimal price) {
        LOGGER.info(Log.PERIODICAL_UPDATE + name);
        return DAO.update(new PeriodicalEdition(id, safeInput(name), price));
    }

    public boolean deletePeriodical(int id) {
        LOGGER.info(Log.PERIODICAL_DELETE + id);
        return DAO.delete(id);
    }

    public String safeInput(String s) {
        return s.replaceAll("<[^>]*>|[@#$%^&*;/|<>\"']+", "");
    }
}
