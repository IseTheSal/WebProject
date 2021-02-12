package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.dao.impl.GameDaoImpl;
import by.learning.web.model.dao.impl.OrderDaoImpl;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.OrderService;
import by.learning.web.validator.OrderValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();

    private final GameDao gameDao = GameDaoImpl.getInstance();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();


    public int findCouponDiscount(String code) throws ServiceException {
        int discount = 0;
        boolean couponValid = OrderValidator.isCouponValid(code);
        logger.log(Level.DEBUG, " VALID {}", couponValid);
        logger.log(Level.DEBUG, " CODE {}", code);
        if (couponValid) {
            try {
                discount = orderDao.findCouponDiscount(code);
                if (discount > 0) {
                    logger.log(Level.INFO, "coupon with name {} exist", code);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return discount;
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

    @Override
    public void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException {
        try {
            Optional<Game> gameById = gameDao.findGameById(gameId);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                cartMap.remove(game);
                logger.log(Level.INFO, "{} was removed from cart", game.getTitle());
            } else {
                logger.log(Level.INFO, "game with id - {} was not found", gameId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws
            ServiceException {
        try {
            Optional<Game> gameById = gameDao.findGameById(gameId);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                if (cartMap.containsKey(game)) {
                    changeAmount(cartMap, game, operation);
                } else {
                    logger.log(Level.INFO, "Cart map haven`t game {}", game);
                }
            } else {
                logger.log(Level.INFO, "This id game {} was not found", gameById);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void changeAmount(HashMap<Game, Integer> cartMap, Game game, String operation) throws
            ServiceException {
        Integer amount = cartMap.get(game);
        switch (operation) {
            case "+": {
                amount++;
                cartMap.put(game, amount);
                break;
            }
            case "-": {
                if (amount == 1) {
                    removeGameFromCart(cartMap, game.getId());
                } else {
                    amount--;
                    cartMap.put(game, amount);
                }
                break;
            }
        }
    }
}
