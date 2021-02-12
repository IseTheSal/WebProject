package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;

public interface OrderDao extends CloseableDao {
    int findCouponDiscount(String codeName) throws DaoException;
}
