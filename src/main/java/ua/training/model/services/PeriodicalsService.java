package ua.training.model.services;

import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;

import java.math.BigDecimal;

public class PeriodicalsService {
    private static final PeriodicalDao DAO = MysqlDaoFactory.getInstance().getPeriodicalDao();
    private static PeriodicalsService instance;

    private PeriodicalsService() {}

    public static synchronized PeriodicalsService getInstance() {
        if (instance == null) {
            instance = new PeriodicalsService();
        }
        return instance;
    }

    public boolean addPeriodical(int id, String name, BigDecimal price) {
        return DAO.insert(new PeriodicalEdition(id, name, price));
    }

    public boolean updatePeriodical(int id, String name, BigDecimal price) {
        return DAO.update(new PeriodicalEdition(id, name, price));
    }
}
