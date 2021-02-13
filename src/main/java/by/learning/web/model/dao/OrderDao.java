package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;

import java.util.Optional;

public interface OrderDao extends CloseableDao {
    Optional<Coupon> findAvailableCouponByCode(String codeName) throws DaoException;

    boolean createOrder(ClientOrder order) throws DaoException;
}
