package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <pre>Game dao interface.</pre>
 *
 * @author Illia Aheyeu
 */
public interface GameDao extends BaseDao {

    /**
     * Searching game by its id.
     *
     * @param gameId Id of {@link Game game}
     * @return Optional {@link Game game}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Optional<Game> findGameById(int gameId) throws DaoException;

    /**
     * Search all {@link Game games}.
     *
     * @return List of {@link Game games}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    List<Game> findAllGames() throws DaoException;

    /**
     * Search {@link Game game} amount.
     *
     * @param gameId Id of {@link Game game}
     * @return Amount of certain {@link Game game}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    int findGameAmount(int gameId) throws DaoException;

    /**
     * Create and add new {@link Game game} with certain genres and categories.
     *
     * @param game         {@link Game game}
     * @param genresId     array with genre id
     * @param categoriesId array with category id
     * @return <code>true</code> if game was added, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean createGame(Game game, int[] genresId, int[] categoriesId) throws DaoException;

    /**
     * Search all {@link Game game} categories.
     *
     * @return <code>HashMap</code> where Integer - category id, String - category title
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    HashMap<Integer, String> findAllCategories() throws DaoException;

    /**
     * Search all {@link Game game} genres.
     *
     * @return <code>HashMap</code> where Integer - genre id, String - genre title
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    HashMap<Integer, String> findAllGenres() throws DaoException;

    /**
     * Search certain {@link Game game} categories.
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>HashMap</code> where Integer - category id, String - category title
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    HashMap<Integer, String> findGameCategories(int gameId) throws DaoException;

    /**
     * Search certain {@link Game game} genres.
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>HashMap</code> where Integer - genre id, String - genre title
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    HashMap<Integer, String> findGameGenres(int gameId) throws DaoException;

    /**
     * Edit {@link Game game}.
     *
     * @param game         {@link Game game}
     * @param genresId     array with genre id
     * @param categoriesId array with category id
     * @return <code>true</code> if game was edited, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean editGame(Game game, int[] genresId, int[] categoriesId) throws DaoException;
}
