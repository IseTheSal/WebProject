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
 * <pre>Command provides users with role Guest or Client remove {@link Game game} from cart.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class RemoveFromCartCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String gameIdString = request.getParameter(RequestParameter.GAME_ID);
        int gameId = Integer.parseInt(gameIdString);
        HttpSession session = request.getSession();
        HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
        try {
            orderService.removeGameFromCart(cartMap, gameId);
            session.setAttribute(SessionAttribute.CART_MAP, cartMap);
            int cartAmount = orderService.countCartAmount(cartMap);
            session.setAttribute(SessionAttribute.CART_AMOUNT, cartAmount);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
