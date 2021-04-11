package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.service.OrderService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <pre>Command allows users with role Admin find all {@link by.learning.web.model.entity.Coupon coupons}.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Coupon
 */
public class OpenCouponListCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_COUPON_LIST_PAGE;
        try {
            List<Coupon> allCoupons = orderService.findAllCoupons();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.COUPON_LIST, allCoupons);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.FAIL, true);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
