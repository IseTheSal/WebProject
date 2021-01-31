package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;

import java.util.List;

public interface GameService {

    List<Game> findAllGames() throws ServiceException;

}
