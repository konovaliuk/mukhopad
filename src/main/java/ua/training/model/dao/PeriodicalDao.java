package ua.training.model.dao;

import ua.training.model.entities.PeriodicalEdition;

import java.util.List;

/**
 * Data Access object for periodical_editions table.
 *
 * @author Olksandr Mukhopad
 */
public interface PeriodicalDao {
    /**
     * Finds all PeriodicalEditions in database.
     * @return list of all editions
     */
    List<PeriodicalEdition> findAll();

    /**
     * Finds certain PeriodicalEdition by its unique id.
     * @param editionId id of edition
     * @return periodical edition
     */
    PeriodicalEdition findById(int editionId);

    /**
     * Finds certain PeriodicalEdition by its unique name.
     * @param Name name of the edition
     * @return periodical edition
     */
    PeriodicalEdition findByName(String Name);

    /**
     * Tries to insert PeriodicalEdition into database
     * @param edition Periodical edition
     * @return true if insertion was successful, false otherwise
     */
    boolean insert(PeriodicalEdition edition);

    /**
     * Tries to update PeriodicalEdition in database
     * @param edition Periodical edition
     * @return true if update was successful, false otherwise
     */
    boolean update(PeriodicalEdition edition);

    /**
     * Tries to delete PeriodicalEdition from database
     * @param id id of edition
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(int id);
}
