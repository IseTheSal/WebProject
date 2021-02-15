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
        //fixme
        //filter to redirect login
        HttpSession session = request.getSession();
        short discount = (short) session.getAttribute(SessionAttribute.COUPON_DISCOUNT);
        boolean isCouponExist = false;
        Coupon coupon = null;
        if (discount != 0) {
            coupon = (Coupon) session.getAttribute(SessionAttribute.COUPON);
            isCouponExist = true;
            try {
                discount = orderService.findCouponDiscount(coupon.getCodeName());
                coupon.setDiscount(discount);
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
                //fixme
            }
        }
        if (isCouponExist) {
            try {
                int couponAmount = orderService.findAvailableCouponAmount(coupon.getCodeName());
                if (couponAmount <= 0) {
                    isCouponExist = false;
                } else {
                    orderService.decreaseCouponAmount(coupon.getCodeName(), 1);
                }
            } catch (ServiceException e) {
                //fixme
                logger.log(Level.ERROR, e);
            }
        }
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
        try {
            boolean isOrderCreated = orderService.createOrder(user.getId(), cartMap, coupon);
            if (isOrderCreated) {
                orderService.sendGameCodeToUser(cartMap, user);
                //fixme orderCreated request parameter
                session.setAttribute(SessionAttribute.CART_AMOUNT, 0);
                session.setAttribute(SessionAttribute.CART_MAP, new HashMap<Game, Integer>());
                short newDiscount = 0;
                session.setAttribute(SessionAttribute.COUPON_DISCOUNT, newDiscount);
                session.removeAttribute(SessionAttribute.COUPON);
                page = PagePath.INDEX;
            } else if (isCouponExist) {
                //fixme orderCreated request parameter. Cart map was changed to available amount
                orderService.increaseCouponAmount(coupon.getCodeName(), 1);
            }
        } catch (ServiceException e) {
            //fixme
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
