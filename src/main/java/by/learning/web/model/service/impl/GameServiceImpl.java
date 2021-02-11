package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.impl.GameDaoImpl;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class GameServiceImpl implements GameService {

    private static final Logger logger = LogManager.getLogger();
    private final GameDao gameDao = GameDaoImpl.getInstance();


    public List<Game> findAllGames() throws ServiceException {
        List<Game> result;
        try {
            result = gameDao.findAllGames();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean isGameInStock(int gameId) throws ServiceException {
        boolean result;
        try {
            int gameAmount = gameDao.findGameCount(gameId);
            if (gameAmount > 0) {
                result = true;
            } else {
                result = false;
                logger.log(Level.INFO, "Game with id {} out of stock", gameId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Optional<Game> findGameById(int id) throws ServiceException {
        Optional<Game> result = Optional.empty();
        if (id > 0) {
            try {
                result = gameDao.findGameById(id);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.INFO, "Id {} less than zero", id);
        }
        return result;
    }

    @Override
    public void addGameToCart(Game game, HashMap<Game, Integer> hashMap) {
        if (hashMap.containsKey(game)) {
            Integer amount = hashMap.get(game);
            amount++;
            logger.log(Level.INFO, amount + " " + game.getTitle() + " was added to cart");
            hashMap.put(game, amount);
        } else {
            hashMap.put(game, 1);
        }
    }

    @Override
    public int countCartAmount(HashMap<Game, Integer> hashMap) {
        int totalAmount = 0;
        Collection<Integer> gameAmount = hashMap.values();
        for (Integer amount : gameAmount) {
            totalAmount += amount;
        }
        return totalAmount;
    }
}
