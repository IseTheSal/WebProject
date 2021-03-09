package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteCouponCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public DeleteCouponCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.ADMIN_COUPON_LIST_PAGE;
        String code = request.getParameter(RequestParameter.COUPON_CODE);
        try {
            boolean isDeleted = orderService.deleteCoupon(code);
            request.setAttribute(isDeleted ? RequestParameter.SUCCESS : RequestParameter.FAIL, true);
            if (isDeleted) {
                HttpSession session = request.getSession();
                List<Coupon> couponList = (List<Coupon>) session.getAttribute(SessionAttribute.COUPON_LIST);
                couponList.removeIf(coupon -> coupon.getCodeName().equals(code));
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
