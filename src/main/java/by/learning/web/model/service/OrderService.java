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
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException;

    /**
     * Searching {@link Coupon}.
     *
     * @param code {@link Coupon} promocode
     * @return Optional {@link Coupon}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Optional<Coupon> findCouponByCode(String code) throws ServiceException;

    /**
     * Check if gamecodes available(amount greater than zero).
     *
     * @param gameId id of certain {@link Game game}
     * @return <code>true</code> if gamecode amount greater than zero, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean isGameInStock(int gameId) throws ServiceException;

    /**
     * Search game amount.
     *
     * @param gameId id of certain {@link Game game}
     * @return {@link Game game} amount value
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
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
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException;

    /**
     * Change amount of game in cart. If amount less then zero, game will be removed from cart.
     *
     * @param cartMap   Client cart, where <code>Integer</code> is total amount of certain {@link Game game}
     * @param gameId    gameId id of certain {@link Game game}
     * @param operation <code>increase</code> or <code>decrease</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws ServiceException;

    /**
     * Create {@link ClientOrder order}.
     *
     * @param user    {@link User user}
     * @param cartMap Client cart, where <code>Integer</code> is total amount of certain {@link Game game}
     * @param coupon  {@link Coupon} with discount
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if order was created return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with  {@link by.learning.web.validator.ValidationInformation FAIL}  and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> createOrder(User user, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException;

    /**
     * Decrease {@link Coupon} amount.
     *
     * @param codeName {@link Coupon} promocode
     * @param amount   amount to decrease
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    void decreaseCouponAmount(String codeName, int amount) throws ServiceException;

    /**
     * Increase amount.
     *
     * @param codeName {@link Coupon} promocode
     * @param amount   amount to increase
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    void increaseCouponAmount(String codeName, int amount) throws ServiceException;

    /**
     * Search {@link Coupon} discount.
     *
     * @param codeName {@link Coupon} promocode
     * @return {@link Coupon} discount
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    short findCouponDiscount(String codeName) throws ServiceException;

    /**
     * Decrease available {@link Coupon} amount (amount greater than zero).
     *
     * @param codeName       {@link Coupon} promocode
     * @param decreaseAmount amount to decrease
     * @return <code>true</code> if amount was decreased, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws ServiceException;

    /**
     * Search order history of Client.
     *
     * @param userId id of certain User
     * @return <code>List</code> of bought games
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    List<Game> findOrderHistory(int userId) throws ServiceException;

    /**
     * Search total price of all Client`s orders.
     *
     * @param userId id of certain user
     * @return total price of all orders
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    BigDecimal findOrderPrice(int userId) throws ServiceException;

    /**
     * Add gamecode.
     *
     * @param gameId id of certain game
     * @param code   {@link Game} gamecode
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if gamecode was added return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with  {@link by.learning.web.validator.ValidationInformation FAIL}  and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> addGameCode(String gameId, String code) throws ServiceException;

    /**
     * Search all {@link Coupon coupons}.
     *
     * @return <code>List</code> of {@link Coupon coupons}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    List<Coupon> findAllCoupons() throws ServiceException;

    /**
     * Create {@link Coupon}.
     *
     * @param discount {@link Coupon} discount
     * @param code     {@link Coupon} promocode
     * @param amount   {@link Coupon} amount
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if coupon was created return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with  {@link by.learning.web.validator.ValidationInformation FAIL}  and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> createCoupon(String discount, String code, String amount) throws ServiceException;

    /**
     * Delete {@link Coupon}.
     *
     * @param code {@link Coupon} promocode
     * @return <code>true</code> if coupon was deleted, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean deleteCoupon(String code) throws ServiceException;

    /**
     * Search all User`s orders.
     *
     * @return <code>Hashmap</code> with {@link User}-key and {@link ClientOrder} - value
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    HashMap<User, ClientOrder> findAllOrders() throws ServiceException;
}
