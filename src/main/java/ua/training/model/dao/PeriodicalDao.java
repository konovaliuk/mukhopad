package ua.training.model.dao;

import ua.training.model.entities.PeriodicalEdition;

import java.util.List;

public interface PeriodicalDao {

    List<PeriodicalEdition> findAll();

    PeriodicalEdition findById(int editionId);

    PeriodicalEdition findByName(String Name);

    boolean insert(PeriodicalEdition subscription);

    boolean update(PeriodicalEdition subscription);

    boolean delete(int id);
}
