package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
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
import java.util.Optional;

public class EditCouponAmountCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public EditCouponAmountCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.ADMIN_COUPON_LIST_PAGE;
        String code = request.getParameter(RequestParameter.COUPON_CODE);
        String amountValue = request.getParameter(RequestParameter.COUPON_AMOUNT);
        int amount = Integer.parseInt(amountValue);
        try {
            Optional<Coupon> couponByCode = orderService.findCouponByCode(code);
            if (couponByCode.isPresent()) {
                int couponAmount = couponByCode.get().getAmount();
                if (couponAmount > 0) {
                    if (couponAmount > amount) {
                        orderService.decreaseCouponAmount(code, couponAmount - amount);
                    } else if (couponAmount < amount) {
                        orderService.increaseCouponAmount(code, amount - couponAmount);
                    }
                    HttpSession session = request.getSession();
                    List<Coupon> couponList = (List<Coupon>) session.getAttribute(SessionAttribute.COUPON_LIST);
                    couponList.stream().filter(coupon -> coupon.getCodeName().equals(code)).findFirst().ifPresent(coupon -> coupon.setAmount(amount));
                    request.setAttribute(RequestParameter.SUCCESS, true);
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.FAIL, true);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
