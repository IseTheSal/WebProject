package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GameService {

    Optional<Game> findGameById(int id) throws ServiceException;

    List<Game> findAllGames() throws ServiceException;

    Set<String> createGame(String gameTitle, String imagePath,
                           String description, String price, String trailerLink,
                           String[] genres, String[] categories) throws ServiceException;

    HashMap<Integer, String> findAllGenres() throws ServiceException;

    HashMap<Integer, String> findAllCategories() throws ServiceException;

}

