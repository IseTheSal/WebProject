package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;

import java.util.HashMap;

public interface OrderService {
    int findCouponDiscount(String codeName) throws ServiceException;

    boolean isGameInStock(int gameId) throws ServiceException;

    void addGameToCart(Game game, HashMap<Game, Integer> hashMap);

    int countCartAmount(HashMap<Game, Integer> hashMap);

    void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException;

    void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws ServiceException;

}
