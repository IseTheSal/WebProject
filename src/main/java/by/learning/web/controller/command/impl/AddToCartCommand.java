package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.OrderService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * <pre>Command allows users with roles Guest or Client add game to cart</pre>
 *
 * @author Illia Aheyeu
 */
public class AddToCartCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String gameId = request.getParameter(RequestParameter.GAME_ID);
        int id = Integer.parseInt(gameId);
        try {
            boolean gameInStock = orderService.isGameInStock(id);
            if (gameInStock) {
                HttpSession session = request.getSession();
                HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
                Game game = (Game) session.getAttribute(SessionAttribute.CURRENT_GAME);
                orderService.addGameToCart(game, cartMap);
                int cartAmount = orderService.countCartAmount(cartMap);
                session.setAttribute(SessionAttribute.CART_AMOUNT, cartAmount);
            } else {
                request.setAttribute(RequestParameter.GAME_IN_STOCK, false);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
