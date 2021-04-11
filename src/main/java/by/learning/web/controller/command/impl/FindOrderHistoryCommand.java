package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.OrderService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

/**
 * <pre>Command allows users with role Client find their order history and total price of all orders.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.ClientOrder
 */
public class FindOrderHistoryCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        int userId = user.getId();
        try {
            List<Game> orderHistory = orderService.findOrderHistory(userId);
            if (!orderHistory.isEmpty()) {
                request.setAttribute(RequestParameter.ORDER_HISTORY_LIST, orderHistory);
            } else {
                request.setAttribute(RequestParameter.FAIL, true);
                logger.log(Level.INFO, "user with id - {} has empty order history", userId);
            }
            BigDecimal orderPrice = orderService.findOrderPrice(userId);
            request.setAttribute(RequestParameter.ORDER_HISTORY_PRICE, orderPrice);
        } catch (ServiceException ex) {
            logger.log(Level.ERROR, ex);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
