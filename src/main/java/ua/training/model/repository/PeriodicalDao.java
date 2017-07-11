package ua.training.model.repository;

import ua.training.model.dto.PeriodicalEditionDTO;

import java.util.List;

/**
 * Data Access object for periodical_editions table.
 *
 * @author Olksandr Mukhopad
 */
public interface PeriodicalRepository {
    /**
     * Finds all PeriodicalEditions in database.
     * @return list of all editions
     */
    List<PeriodicalEditionDTO> findAll();

    /**
     * Finds certain PeriodicalEdition by its unique id.
     * @param editionId id of edition
     * @return periodical edition
     */
    PeriodicalEditionDTO findById(int editionId);

    /**
     * Finds certain PeriodicalEdition by its unique name.
     * @param Name name of the edition
     * @return periodical edition
     */
    PeriodicalEditionDTO findByName(String Name);

    /**
     * Tries to insert PeriodicalEdition into database
     * @param edition Periodical edition
     * @return true if insertion was successful, false otherwise
     */
    boolean insert(PeriodicalEditionDTO edition);

    /**
     * Tries to update PeriodicalEdition in database
     * @param edition Periodical edition
     * @return true if update was successful, false otherwise
     */
    boolean update(PeriodicalEditionDTO edition);

    /**
     * Tries to delete PeriodicalEdition from database
     * @param id id of edition
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(int id);
}
