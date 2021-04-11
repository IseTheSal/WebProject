package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.service.OrderService;
import by.learning.web.model.service.impl.ServiceInstance;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>Command allows users with role Admin create new discount coupon.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Coupon
 */
public class CreateCouponCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_MENU_PAGE;
        String code = request.getParameter(RequestParameter.COUPON_CODE);
        Optional<Coupon> coupon;
        try {
            coupon = orderService.findCouponByCode(code);
            if (coupon.isPresent()) {
                request.setAttribute(RequestParameter.COUPON_EXIST, true);
                request.setAttribute(RequestParameter.FAIL, true);
            } else {
                String discount = request.getParameter(RequestParameter.COUPON_DISCOUNT);
                String amount = request.getParameter(RequestParameter.COUPON_AMOUNT);
                Set<String> validInfo = orderService.createCoupon(discount, code, amount);
                if (validInfo.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                    request.setAttribute(RequestParameter.SUCCESS, true);
                } else if (validInfo.remove(ValidationInformation.FAIL.getInfoValue())) {
                    request.setAttribute(RequestParameter.FAIL, true);
                    request.setAttribute(RequestParameter.VALID_ISSUES, validInfo);
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
