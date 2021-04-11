package by.learning.web.model.service.impl;

import by.learning.web.model.service.GameService;
import by.learning.web.model.service.OrderService;
import by.learning.web.model.service.UserService;

/**
 * <pre>Hold service implementation objects.</pre>
 *
 * @author Illia Aheyeu
 */
public enum ServiceInstance {
    INSTANCE;

    private final GameService gameService = new GameServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    public GameService getGameService() {
        return gameService;
    }

    public UserService getUserService() {
        return userService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
