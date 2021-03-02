package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface GameDao extends BaseDao {

    Optional<Game> findGameById(int id) throws DaoException;

    List<Game> findAllGames() throws DaoException;

    int findGameCount(int gameId) throws DaoException;

    boolean createGame(Game game, int[] genresId, int[] categoriesId) throws DaoException;

    HashMap<Integer, String> findAllCategories() throws DaoException;

    HashMap<Integer, String> findAllGenres() throws DaoException;

}
