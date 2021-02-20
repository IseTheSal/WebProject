package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class MakeOrderCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public MakeOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user == null) {
            page = PagePath.LOGIN;
            request.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            return page;
        }
        short discount = (short) session.getAttribute(SessionAttribute.COUPON_DISCOUNT);
        boolean isCouponExist = false;
        Coupon coupon = null;
        if (discount != 0) {
            coupon = (Coupon) session.getAttribute(SessionAttribute.COUPON);
            isCouponExist = true;
        }
        if (isCouponExist) {
            try {
                int couponAmount = orderService.findAvailableCouponAmount(coupon.getCodeName());
                if (couponAmount <= 0) {
                    isCouponExist = false;
                    coupon = null;
                } else {
                    discount = orderService.findCouponDiscount(coupon.getCodeName());
                    coupon.setDiscount(discount);
                    orderService.decreaseCouponAmount(coupon.getCodeName(), 1);
                }
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.SERVER_ERROR, true);
                logger.log(Level.ERROR, e);
            }
        }
        HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
        try {
            boolean isOrderCreated = orderService.createOrder(user.getId(), cartMap, coupon);
            if (isOrderCreated) {
                orderService.sendGameCodeToUser(cartMap, user);
                request.setAttribute(RequestParameter.ORDER_CREATED, true);
                session.setAttribute(SessionAttribute.CART_AMOUNT, 0);
                session.setAttribute(SessionAttribute.CART_MAP, new HashMap<Game, Integer>());
                short newDiscount = 0;
                session.setAttribute(SessionAttribute.COUPON_DISCOUNT, newDiscount);
                session.removeAttribute(SessionAttribute.COUPON);
                page = PagePath.INDEX;
            } else {
                session.setAttribute(SessionAttribute.CART_AMOUNT, orderService.countCartAmount(cartMap));
                request.setAttribute(RequestParameter.CART_AMOUNT_CHANGED, true);
            }
            if (isCouponExist && !isOrderCreated) {
                orderService.increaseCouponAmount(coupon.getCodeName(), 1);
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
