package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * <pre>Order dao interface.</pre>
 *
 * @author Illia Aheyeu
 */
public interface OrderDao extends BaseDao {
    /**
     * Searching available (amount of coupon greater than zero) {@link Coupon}.
     *
     * @param codeName {@link Coupon} promocode
     * @return Optional {@link Coupon}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Optional<Coupon> findAvailableCoupon(String codeName) throws DaoException;

    /**
     * Searching {@link Coupon}.
     *
     * @param codeName {@link Coupon} promocode
     * @return Optional {@link Coupon}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Optional<Coupon> findCouponByCode(String codeName) throws DaoException;

    /**
     * Decrease available (amount of coupon greater than zero) coupon amount.
     *
     * @param codeName       {@link Coupon} promocode
     * @param decreaseAmount decreasing <code>int</code> value of coupon amount
     * @return <code>true</code> if decreasing was successful, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws DaoException;

    /**
     * Search {@link Coupon} discount by its promocode.
     *
     * @param codeName {@link Coupon} promocode
     * @return discount value
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    short findCouponDiscountByName(String codeName) throws DaoException;

    /**
     * Search available (amount of gameCode greater than zero) gamecodes.
     *
     * @param gameId id of {@link Game}
     * @return available amount of certain game
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    int findAvailableGameAmountById(int gameId) throws DaoException;

    /**
     * Create {@link ClientOrder}.
     *
     * @param order {@link ClientOrder}
     * @return <code>true</code> if order was created, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean createOrder(ClientOrder order) throws DaoException;

    /**
     * Search gamecodes of certain {@link Game}.
     *
     * @param gameId id of {@link Game}
     * @param amount amount of gamecodes for searching
     * @return <code>List</code> of String with gamecodes
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    List<String> findLimitedGameCodes(int gameId, int amount) throws DaoException;

    /**
     * Set <code>false</code> for each gamecode in <code>List</code>.
     *
     * @param codeList <code>List</code> of gamecodes
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    void putSoldGameCodeList(List<String> codeList) throws DaoException;

    /**
     * Change {@link Coupon} amount by its promocode.
     *
     * @param codeName {@link Coupon} promocode
     * @param amount   changing <code>int</code> value of coupon amount
     * @param increase <code>true</code> if increasing, <code>false</code> if decreasing
     * @return <code>true</code> if change was successfully proceed, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean changeCouponAmountByName(String codeName, int amount, boolean increase) throws DaoException;

    /**
     * Search each {@link ClientOrder} by userId.
     *
     * @param userId {@link User} id
     * @return <code>List</code> of games, which user bought
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    List<Game> findOrderHistoryByUserId(int userId) throws DaoException;

    /**
     * Search total {@link ClientOrder} price.
     *
     * @param userId {@link User} id
     * @return total price
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    BigDecimal findOrderPriceByUserId(int userId) throws DaoException;

    /**
     * Add gamecode of existing {@link Game}.
     *
     * @param gameId {@link Game} id
     * @param code   gamecode
     * @return <code>true</code> if game was added, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean addGameCode(int gameId, String code) throws DaoException;

    /**
     * Search all {@link Coupon coupons}.
     *
     * @return <code>List</code> of {@link Coupon coupons}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    List<Coupon> findAllCoupons() throws DaoException;

    /**
     * Create new {@link Coupon}.
     *
     * @param coupon {@link Coupon}
     * @return <code>true</code> if {@link Coupon coupon} was added, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean createCoupon(Coupon coupon) throws DaoException;

    /**
     * Delete {@link Coupon coupon} by its promocode.
     *
     * @param code {@link Coupon coupon} promocode
     * @return <code>true</code> if {@link Coupon coupon} was deleted, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean deleteCoupon(String code) throws DaoException;
}
