package by.learning.web.model.dao.impl;

import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.dao.UserDao;

public enum DaoInstance {
    INSTANCE;

    private final GameDao gameDao = new GameDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    public GameDao getGameDao() {
        return gameDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }
}
