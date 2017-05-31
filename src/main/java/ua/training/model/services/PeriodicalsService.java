package ua.training.model.services;

import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.mysql.MysqlDaoFactory;
import ua.training.model.entities.PeriodicalEdition;

import javax.servlet.http.*;
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

    private static final String EDITION_ID = "editionId";
    private static final String EDITION_NAME = "editionName";
    private static final String EDITION_PRICE = "editionPrice";

    public void addPeriodical(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        int id = Integer.parseInt((String) session.getAttribute(EDITION_ID));
        String name = (String) session.getAttribute(EDITION_NAME);
        BigDecimal price = new BigDecimal((String) session.getAttribute(EDITION_PRICE));

        DAO.insert(new PeriodicalEdition(id, name, price));
    }
    
    public void updatePeriodical(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        int id = Integer.parseInt((String) session.getAttribute(EDITION_ID));
        String name = (String) session.getAttribute(EDITION_NAME);
        BigDecimal price = new BigDecimal((String) session.getAttribute(EDITION_PRICE));

        DAO.update(new PeriodicalEdition(id, name, price));
    }

    public void deletePeriodical(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt((String) request.getSession().getAttribute(EDITION_ID));
        DAO.delete(id);
    }


}
