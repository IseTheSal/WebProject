package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;

import java.util.HashMap;
import java.util.Optional;

public interface OrderService {
    Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException;

    boolean isGameInStock(int gameId) throws ServiceException;

    void addGameToCart(Game game, HashMap<Game, Integer> hashMap);

    int countCartAmount(HashMap<Game, Integer> hashMap);

    void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException;

    void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws ServiceException;

    boolean createOrder(int userId, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException;

    void decreaseCouponAmount(String codeName, int amount) throws ServiceException;

    void increaseCouponAmount(String codeName, int amount) throws ServiceException;

    short findCouponDiscount(String codeName) throws ServiceException;

    int findAvailableCouponAmount(String codeName) throws ServiceException;

    void sendGameCodeToUser(HashMap<Game, Integer> cartMap, User user) throws ServiceException;
}