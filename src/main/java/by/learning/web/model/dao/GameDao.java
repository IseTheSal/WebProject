package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.Game;

import java.util.List;

public interface GameDao extends CloseableDao{

    List<Game> findAllGames() throws DaoException;

}
