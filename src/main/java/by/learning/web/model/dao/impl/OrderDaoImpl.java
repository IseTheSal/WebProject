package by.learning.web.model.dao.impl;


import by.learning.web.exception.ConnectionPoolException;
import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();

    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;

    private static final String FIND_AVAILABLE_COUPON_DISCOUNT = "SELECT coupon_id, discount " +
            "FROM coupons " +
            "WHERE code = ? AND amount > 0";
    private static final String FIND_COUPON_BY_CODE = "SELECT coupon_id, discount, amount " +
            "FROM coupons " +
            "WHERE code = ?";
    private static final String INSERT_ORDER_WITH_COUPON = "INSERT INTO orders(order_id, user_id, total_price, coupon_id) " +
            "VALUES (default, ?, ?, ?)";
    private static final String INSERT_ORDER_WITHOUT_COUPON = "INSERT INTO orders(order_id, user_id, total_price, coupon_id) " +
            "VALUES (default, ?, ?, null)";
    private static final String INSERT_GAME_ORDER = "INSERT INTO game_order(game_id, order_id) " +
            "VALUES (?, ?)";
    private static final String FIND_AVAILABLE_GAME_CODE_AMOUNT = "SELECT count(game_code) AS amount " +
            "FROM codes " +
            "WHERE sold IS FALSE AND game_id = ?";
    private static final String FIND_AVAILABLE_COUPONS_AMOUNT = "SELECT amount " +
            "FROM coupons " +
            "WHERE code = ? AND amount > 0 ";
    private static final String FIND_COUPON_DISCOUNT = "SELECT discount " +
            "FROM coupons " +
            "WHERE code = ?;";
    private static final String FIND_LIMITED_GAME_CODE = "SELECT game_code " +
            "FROM codes " +
            "WHERE sold IS FALSE " +
            "  AND game_id = ? " +
            "LIMIT ?";
    private static final String SET_CODE_SOLD_TRUE = "UPDATE codes " +
            "SET sold = TRUE " +
            "WHERE game_code = ?";
    private static final String INCREASE_COUPON_AMOUNT = "UPDATE coupons " +
            "SET amount = amount + ? " +
            "WHERE code = ?";
    private static final String DECREASE_COUPON_AMOUNT = "UPDATE coupons " +
            "SET amount = amount - ? " +
            "WHERE code = ?";
    private static final String DECREASE_AVAILABLE_COUPON_AMOUNT = "UPDATE coupons " +
            "SET amount = amount - ? " +
            "WHERE code = ? AND amount > 0";
    private static final String FIND_ORDER_HISTORY = "SELECT games.game_id, title, image_path " +
            "FROM games " +
            "         INNER JOIN game_order go ON games.game_id = go.game_id " +
            "         INNER JOIN orders ON go.order_id = orders.order_id " +
            "WHERE orders.user_id = ?";
    private static final String FIND_ORDER_PRICE = "SELECT total_price " +
            "FROM orders " +
            "WHERE user_id = ?";
    private static final String ADD_GAMECODE = "INSERT INTO codes(code_id, game_code, sold, game_id) " +
            "VALUES (default, ?, default, ?)";
    private static final String FIND_ALL_COUPONS = "SELECT coupon_id, code, discount, amount FROM coupons";
    private static final String CREATE_COUPON = "INSERT INTO coupons(coupon_id, code, discount, amount) VALUES (default, ?, ?, ?)";
    private static final String DELETE_COUPON = "DELETE FROM coupons WHERE code = ?";


    @Override
    public Optional<Coupon> findAvailableCouponDiscount(String code) throws DaoException {
        Optional<Coupon> result = Optional.empty();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_AVAILABLE_COUPON_DISCOUNT);
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int couponId = resultSet.getInt(1);
                short discount = resultSet.getShort(2);
                Coupon coupon = new Coupon(couponId, discount, code);
                result = Optional.of(coupon);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public int findAvailableCouponAmountByName(String codeName) throws DaoException {
        int amount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_AVAILABLE_COUPONS_AMOUNT);
            preparedStatement.setString(1, codeName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        logger.log(Level.INFO, "available amount {}", amount);
        return amount;
    }


    @Override
    public boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws DaoException {
        boolean isDecreased = false;
        PreparedStatement preparedStatement = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(DECREASE_AVAILABLE_COUPON_AMOUNT);
            preparedStatement.setInt(1, decreaseAmount);
            preparedStatement.setString(2, codeName);
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                isDecreased = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
        }
        return isDecreased;
    }

    @Override
    public short findCouponDiscountByName(String codeName) throws DaoException {
        short discount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_COUPON_DISCOUNT);
            preparedStatement.setString(1, codeName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                discount = resultSet.getShort(1);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        logger.log(Level.INFO, "{} discount {}", codeName, discount);
        return discount;
    }

    @Override
    public boolean changeCouponAmountByName(String codeName, int amount, boolean increase) throws DaoException {
        boolean changed = false;
        PreparedStatement preparedStatement = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            if (increase) {
                preparedStatement = connection.prepareStatement(INCREASE_COUPON_AMOUNT);
            } else {
                preparedStatement = connection.prepareStatement(DECREASE_COUPON_AMOUNT);
            }
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, codeName);
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                changed = true;
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(preparedStatement);
        }
        return changed;
    }

    @Override
    public List<Game> findOrderHistoryByUserId(int userId) throws DaoException {
        List<Game> result = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_ORDER_HISTORY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gameId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imagePath = resultSet.getString(3);
                Game game = new Game(gameId, title, imagePath);
                result.add(game);
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public BigDecimal findOrderPriceByUserId(int userId) throws DaoException {
        BigDecimal totalPrice = new BigDecimal("0.0");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_ORDER_PRICE);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalPrice = totalPrice.add(resultSet.getBigDecimal(1));
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return totalPrice;
    }

    @Override
    public int findAvailableGameAmountById(int gameId) throws DaoException {
        int amount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            preparedStatement = connection.prepareStatement(FIND_AVAILABLE_GAME_CODE_AMOUNT);
            preparedStatement.setInt(1, gameId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt(1);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        logger.log(Level.INFO, "available amount {}", amount);
        return amount;
    }

    @Override
    public boolean createOrder(ClientOrder order) throws DaoException {
        boolean isCreated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = CONNECTION_POOL.takeConnection();
            connection.setAutoCommit(false);
            if (order.getCoupon() == null) {
                preparedStatement = connection.prepareStatement(INSERT_ORDER_WITHOUT_COUPON, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(INSERT_ORDER_WITH_COUPON, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(3, order.getCoupon().getId());
            }
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setBigDecimal(2, order.getPrice());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                boolean executed = true;
                HashMap<Game, Integer> gameMap = order.getGameMap();
                Set<Game> keySet = new HashSet<>(gameMap.keySet());
                int orderId = generatedKeys.getInt(1);
                for (Game game : keySet) {
                    int value = gameMap.get(game);
                    int i = 0;
                    while (i < value && executed) {
                        executed = relateGameOrder(connection, game.getId(), orderId);
                        i++;
                    }
                }
                isCreated = executed;
            }
        } catch (ConnectionPoolException | SQLException e) {
            rollback(connection);
            throw new DaoException(e);
        } finally {
            setAutoCommitTrue(connection);
            close(generatedKeys);
            close(preparedStatement);
        }
        return isCreated;
    }

    private boolean relateGameOrder(Connection connection, int gameId, int orderId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GAME_ORDER)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, orderId);
            int executeUpdate = preparedStatement.executeUpdate();
            return (executeUpdate > 0);
        }
    }

    @Override
    public List<String> findLimitedGameCodes(int gameId, int amount) throws DaoException {
        List<String> result = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_LIMITED_GAME_CODE)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, amount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String code = resultSet.getString(1);
                result.add(code);
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
        }
        return result;
    }

    @Override
    public void putSoldGameCodeList(List<String> codeList) throws DaoException {
        try (Connection connection = CONNECTION_POOL.takeConnection()) {
            for (String codeName : codeList) {
                putSoldOnGameCode(connection, codeName);
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
    }

    private void putSoldOnGameCode(Connection connection, String codeName) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SET_CODE_SOLD_TRUE)) {
            preparedStatement.setString(1, codeName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean addGameCode(int gameId, String code) throws DaoException {
        boolean codeAdded;
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_GAMECODE)) {
            preparedStatement.setString(1, code);
            preparedStatement.setInt(2, gameId);
            int executeUpdate = preparedStatement.executeUpdate();
            codeAdded = (executeUpdate > 0);
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
        return codeAdded;
    }

    @Override
    public List<Coupon> findAllCoupons() throws DaoException {
        List<Coupon> result = new ArrayList<>();
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_COUPONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int couponId = resultSet.getInt(1);
                String code = resultSet.getString(2);
                short discount = resultSet.getShort(3);
                int amount = resultSet.getInt(4);
                Coupon coupon = new Coupon(couponId, discount, code, amount);
                result.add(coupon);
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public boolean createCoupon(Coupon coupon) throws DaoException {
        boolean isCreated;
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_COUPON)) {
            preparedStatement.setString(1, coupon.getCodeName());
            preparedStatement.setShort(2, coupon.getDiscount());
            preparedStatement.setInt(3, coupon.getAmount());
            isCreated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
        return isCreated;
    }

    @Override
    public Optional<Coupon> findCouponByCode(String code) throws DaoException {
        Optional<Coupon> coupon = Optional.empty();
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_COUPON_BY_CODE)) {
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int couponId = resultSet.getInt(1);
                short discount = resultSet.getShort(2);
                int amount = resultSet.getInt(3);
                coupon = Optional.of(new Coupon(couponId, discount, code, amount));
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
        }
        return coupon;
    }

    @Override
    public boolean deleteCoupon(String code) throws DaoException {
        boolean isDeleted;
        try (Connection connection = CONNECTION_POOL.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COUPON)) {
            preparedStatement.setString(1, code);
            isDeleted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        }
        return isDeleted;
    }
}
