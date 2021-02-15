package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao {
    Optional<Coupon> findAvailableCouponByCode(String codeName) throws DaoException;

    int findAvailableCouponAmountByName(String codeName) throws DaoException;

    short findCouponDiscountByName(String codeName) throws DaoException;

    int findAvailableGameAmountById(int gameId) throws DaoException;

    boolean createOrder(ClientOrder order) throws DaoException;

    List<String> findLimitedGameCodes(int gameId, int amount) throws DaoException;

    void putSoldGameCodeList(List<String> codeList) throws DaoException;

    boolean changeCouponAmountByName(String codeName, int amount, boolean increase) throws DaoException;
}
