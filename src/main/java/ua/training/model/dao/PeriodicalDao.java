package ua.training.model.dao;

import ua.training.model.entities.PeriodicalEdition;

public interface PeriodicalDao {

    PeriodicalEdition findById(int editionId);

    PeriodicalEdition findByName(String Name);

    boolean insert(PeriodicalEdition subscription);

    boolean update(PeriodicalEdition subscription);

    boolean delete(int id);
}
