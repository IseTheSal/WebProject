package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * <pre>Command allows users with role Admin find all {@link by.learning.web.model.entity.ClientOrder orders}.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.ClientOrder
 */
public class OpenOrderListCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public OpenOrderListCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.ADMIN_ORDER_LIST_PAGE;
        try {
            HashMap<User, ClientOrder> allOrders = orderService.findAllOrders();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.CLIENT_ORDERS_MAP, allOrders);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }

        return page;
    }
}
