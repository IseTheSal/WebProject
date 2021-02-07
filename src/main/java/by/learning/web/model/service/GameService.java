package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {

    Optional<Game> findGameById(int id) throws ServiceException;

    List<Game> findAllGames() throws ServiceException;

    boolean isGameInStock(int gameId) throws ServiceException;

}
