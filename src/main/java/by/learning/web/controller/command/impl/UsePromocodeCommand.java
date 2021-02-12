package by.learning.web.controller.command.impl;

import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UsePromocodeCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public UsePromocodeCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String code = request.getParameter(RequestParameter.COUPON_CODE);
        try {
            int couponDiscount = orderService.findCouponDiscount(code);
            logger.log(Level.DEBUG, "{}", couponDiscount);
            if (couponDiscount > 0) {
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.COUPON_DISCOUNT, couponDiscount);
                request.setAttribute(RequestParameter.COUPON_EXIST, true);
                logger.log(Level.DEBUG, "TURE");
            } else {
                logger.log(Level.DEBUG, "FALSE");
                request.setAttribute(RequestParameter.COUPON_EXIST, false);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
