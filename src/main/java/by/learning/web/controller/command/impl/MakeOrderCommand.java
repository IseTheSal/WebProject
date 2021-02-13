package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MakeOrderCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderServiceImpl orderService;

    public MakeOrderCommand(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
        if (session.getAttribute(SessionAttribute.CURRENT_USER) == null) {
            page = PagePath.LOGIN;
        } else {
            page = request.getParameter(RequestParameter.CURRENT_PAGE);
            User currentUser = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
            Short discount = 0;
            try {
                discount = (Short) session.getAttribute(SessionAttribute.COUPON_DISCOUNT);
            } catch (Exception e) {
                logger.log(Level.DEBUG, e);
            }
            Coupon coupon = null;
            if (discount != 0) {
                coupon = (Coupon) session.getAttribute(SessionAttribute.COUPON);
            }
            int userId = currentUser.getId();
            try {
                boolean created = orderService.createOrder(userId, coupon, cartMap);
                if (created) {
                    session.removeAttribute(SessionAttribute.COUPON);
                    session.setAttribute(SessionAttribute.CART_MAP, new HashMap<Game, Integer>());
                    session.setAttribute(SessionAttribute.COUPON_DISCOUNT, 0);
                    session.setAttribute(SessionAttribute.CART_AMOUNT, 0);
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                //fixme
                // request add parameter that order failed
            }
        }
        return page;
    }
}
