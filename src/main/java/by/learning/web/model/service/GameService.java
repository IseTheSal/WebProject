package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;

import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>Game service interface</pre>
 *
 * @author Illia Aheyeu
 * @see Game
 */
public interface GameService {

    /**
     * Search {@link Game game}.
     *
     * @param id {@link Game game} id
     * @return Optional {@link Game game}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Optional<Game> findGameById(int id) throws ServiceException;

    /**
     * Search all games.
     *
     * @return <code>List</code> of {@link Game games}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    List<Game> findAllGames() throws ServiceException;

    /**
     * Create {@link Game game} and upload Image to server.
     *
     * @param gameTitle   tile of {@link Game game}
     * @param imagePart   image part of {@link Game game}
     * @param description short {@link Game game} description
     * @param price       {@link Game game} price
     * @param trailerLink {@link Game game} trailer link
     * @param genres      {@link Game game} {@link Game.Genre genres}
     * @param categories  {@link Game game} {@link Game.Category categories}
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if game was created return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with  {@link by.learning.web.validator.ValidationInformation FAIL}  and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> createGame(String gameTitle, Part imagePart,
                           String description, String price, String trailerLink,
                           String[] genres, String[] categories) throws ServiceException;

    /**
     * Search all {@link Game game} {@link Game.Genre genres}.
     *
     * @return <code>HashMap</code> where Integer - genre id, String - genre title
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    HashMap<Integer, String> findAllGenres() throws ServiceException;

    /**
     * Search all {@link Game game} {@link Game.Category categories}.
     *
     * @return <code>HashMap</code> where Integer - category id, String - category title
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    HashMap<Integer, String> findAllCategories() throws ServiceException;

    /**
     * Search certain {@link Game game} {@link Game.Category categories}.
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>HashMap</code> where Integer - category id, String - category title
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    HashMap<Integer, String> findGameCategories(int gameId) throws ServiceException;

    /**
     * Search certain {@link Game game} {@link Game.Genre genres}.
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>HashMap</code> where Integer - genre id, String - genre title
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    HashMap<Integer, String> findGameGenres(int gameId) throws ServiceException;

    /**
     * Edit {@link Game game}.
     *
     * @param gameIdValue id of certain {@link Game game}
     * @param gameTitle   tile of {@link Game game}
     * @param imagePath   image path of {@link Game game}
     * @param description short {@link Game game} description
     * @param priceValue  {@link Game game} price
     * @param trailerLink {@link Game game} trailer link
     * @param genres      {@link Game game} {@link Game.Genre genres}
     * @param categories  {@link Game game} {@link Game.Category categories}
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if game was edited return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with  {@link by.learning.web.validator.ValidationInformation FAIL}  and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> editGame(String gameIdValue, String gameTitle, String imagePath,
                         String description, String priceValue, String trailerLink,
                         String[] genres, String[] categories) throws ServiceException;


    /**
     * Filter all {@link Game games} by parameters.
     *
     * @param startPrice starting price point
     * @param endPrice   ending price point
     * @param categories specified categories, which game must include
     * @param genres     specified genres, which game must include
     * @param sortValue  specified ordering value
     * @return <code>List</code> of {@link Game games}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    List<Game> filterAllGames(int startPrice, int endPrice, String[] categories, String[] genres, String sortValue) throws ServiceException;

    /**
     * Sort game list in specific order.
     *
     * @param gameList    <code>List</code> of {@link Game games}
     * @param sortByValue specified sort value
     */
    void orderGameList(List<Game> gameList, String sortByValue);
}

