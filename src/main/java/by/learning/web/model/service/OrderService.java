package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>Client order interface</pre>
 *
 * @author Illia Aheyeu
 * @see ClientOrder
 */
public interface OrderService {
    /**
     * Searching available (amount of coupon greater than zero) {@link Coupon}.
     *
     * @param code {@link Coupon} promocode
     * @return Optional {@link Coupon}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException;

    /**
     * Searching {@link Coupon}.
     *
     * @param code {@link Coupon} promocode
     * @return Optional {@link Coupon}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    Optional<Coupon> findCouponByCode(String code) throws ServiceException;

    /**
     * Check if gamecodes available(amount greater than zero).
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>true</code> if gamecode amount greater than zero, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    boolean isGameInStock(int gameId) throws ServiceException;

    /**
     * Search game amount.
     *
     * @param gameId id of certain {@link Game game}
     * @return {@link Game game} amount value
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    int findCodeAmount(int gameId) throws ServiceException;

    /**
     * Add game to Client cart.
     *
     * @param game    {@link Game game}
     * @param hashMap Client cart, where Integer is total amount of certain {@link Game game}
     * @see User.Role
     */
    void addGameToCart(Game game, HashMap<Game, Integer> hashMap);

    /**
     * Count total amount of all games in Client cart.
     *
     * @param cartMap Client cart, where Integer is total amount of certain {@link Game game}
     * @return total amount of games
     */
    int countCartAmount(HashMap<Game, Integer> cartMap);

    /**
     * Delete game from Client cart.
     *
     * @param cartMap Client cart, where <code>Integer</code> is total amount of certain {@link Game game}
     * @param gameId  id of certain {@link Game game}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException;

    /**
     * Change amount of game in cart. If amount less then zero, game will be removed from cart.
     *
     * @param cartMap   Client cart, where <code>Integer</code> is total amount of certain {@link Game game}
     * @param gameId    gameId id of certain {@link Game game}
     * @param operation <code>increase</code> or <code>decrease</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException} was thrown
     */
    void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws ServiceException;

    boolean createOrder(int userId, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException;

    void decreaseCouponAmount(String codeName, int amount) throws ServiceException;

    void increaseCouponAmount(String codeName, int amount) throws ServiceException;

    short findCouponDiscount(String codeName) throws ServiceException;

    boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws ServiceException;

    void sendGameCodeToUser(HashMap<Game, Integer> cartMap, User user) throws ServiceException;

    List<Game> findOrderHistory(int userId) throws ServiceException;

    BigDecimal findOrderPrice(int userId) throws ServiceException;

    Set<String> addGameCode(String gameId, String code) throws ServiceException;

    List<Coupon> findAllCoupons() throws ServiceException;

    Set<String> createCoupon(String discount, String code, String amount) throws ServiceException;

    boolean deleteCoupon(String code) throws ServiceException;

    HashMap<User, ClientOrder> findAllOrders() throws ServiceException;
}
