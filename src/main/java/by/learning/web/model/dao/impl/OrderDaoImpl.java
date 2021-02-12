package by.learning.web.model.dao.impl;


import by.learning.web.exception.ConnectionPoolException;
import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDaoImpl implements OrderDao {
    private static final Logger logger = LogManager.getLogger();

    private static final OrderDaoImpl INSTANCE = new OrderDaoImpl();

    public static OrderDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;

    private static final String FIND_AVAILABLE_COUPON_DISCOUNT = "SELECT discount " +
            "FROM coupons " +
            "WHERE code = ? AND amount > 0";

    public int findCouponDiscount(String codeName) throws DaoException {
        int discount = 0;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.getConnection()) {
            preparedStatement = connection.prepareStatement(FIND_AVAILABLE_COUPON_DISCOUNT);
            preparedStatement.setString(1, codeName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                discount = resultSet.getInt(1);
                logger.log(Level.DEBUG, "dis {}", discount);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return discount;
    }


}
