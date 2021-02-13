package by.learning.web.model.dao.impl;


import by.learning.web.exception.ConnectionPoolException;
import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

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

    private static final String INSERT_ORDER_WITH_COUPON = "INSERT INTO orders(order_id, user_id, total_price, coupon_id) " +
            "VALUES (default, ?, ?, ?)";

    private static final String INSERT_ORDER_WITHOUT_COUPON = "INSERT INTO orders(order_id, user_id, total_price, coupon_id) " +
            "VALUES (default, ?, ?, null)";

    private static final String INSERT_GAME_ORDER = "INSERT INTO game_order(game_id, order_id) " +
            "VALUES (?, ?)";

    public Optional<Coupon> findAvailableCouponByCode(String codeName) throws DaoException {
        Optional<Coupon> result = Optional.empty();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.getConnection()) {
            preparedStatement = connection.prepareStatement(FIND_AVAILABLE_COUPON_DISCOUNT);
            preparedStatement.setString(1, codeName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int couponId = resultSet.getInt(1);
                short discount = resultSet.getShort(2);
                Coupon coupon = new Coupon(couponId, discount, codeName);
                result = Optional.of(coupon);
                logger.log(Level.DEBUG, "dis {}", discount);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }

    public boolean createOrder(ClientOrder order) throws DaoException {
        boolean isCreated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = CONNECTION_POOL.getConnection();
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
                int orderId = generatedKeys.getInt(1);
                int i = 0;
                boolean executed = true;
                int[] gameIdArray = order.getGameIdSet().stream().mapToInt(Integer::intValue).toArray();
                while (i < gameIdArray.length && executed) {
                    executed = createGameOrderRelation(connection, gameIdArray[i], orderId);
                    i++;
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
            close(connection);
        }
        return isCreated;
    }

    private boolean createGameOrderRelation(Connection connection, int gameId, int orderId) throws DaoException {
        boolean result = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GAME_ORDER)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, orderId);
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                result = true;
            }
        } catch (SQLException exception) {
            logger.log(Level.ERROR, exception);
            throw new DaoException(exception);
        }
        return result;
    }
}
