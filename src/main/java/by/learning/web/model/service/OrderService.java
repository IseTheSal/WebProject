package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderService {
    Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException;

    Optional<Coupon> findCouponByCode(String code) throws ServiceException;

    boolean isGameInStock(int gameId) throws ServiceException;

    int findCodeAmount(int gameId) throws ServiceException;

    void addGameToCart(Game game, HashMap<Game, Integer> hashMap);

    int countCartAmount(HashMap<Game, Integer> hashMap);

    void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException;

    void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws ServiceException;

    boolean createOrder(int userId, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException;

    //fixme
    void decreaseCouponAmount(String codeName, int amount) throws ServiceException;

    void increaseCouponAmount(String codeName, int amount) throws ServiceException;

    short findCouponDiscount(String codeName) throws ServiceException;

    boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws ServiceException;

    //fixme
    int findAvailableCouponAmount(String codeName) throws ServiceException;

    void sendGameCodeToUser(HashMap<Game, Integer> cartMap, User user) throws ServiceException;

    List<Game> findOrderHistory(int userId) throws ServiceException;

    BigDecimal findOrderPrice(int userId) throws ServiceException;

    Set<String> addGameCode(String gameId, String code) throws ServiceException;

    List<Coupon> findAllCoupons() throws ServiceException;

    Set<String> createCoupon(String discount, String code, String amount) throws ServiceException;

    boolean deleteCoupon(String code) throws ServiceException;
}
