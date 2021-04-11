package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Coupon;
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
import java.util.HashMap;

/**
 * <pre>Command provides users with role Client process their order and sent gamecodes to their emails.</pre>
 * <p>Cases when the order will not be processed:</p>
 * <p>
 * If cart is empty.
 * <br></br>
 * If {@link by.learning.web.model.entity.Game game} is unavailable, decrease amount or delete from cart.
 * <br></br>
 * If {@link by.learning.web.model.entity.Coupon coupon} is invalid anymore, remove discount.
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.ClientOrder
 * @see by.learning.web.model.entity.Coupon
 */
public class MakeOrderCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final OrderService orderService = ServiceInstance.INSTANCE.getOrderService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.CART_PAGE;
        HttpSession session = request.getSession();
        HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
        if (cartMap.isEmpty()) {
            request.setAttribute(RequestParameter.CART_IS_EMPTY, true);
            return page;
        }
        short discount = (short) session.getAttribute(SessionAttribute.COUPON_DISCOUNT);
        Coupon coupon = null;
        if (discount != 0) {
            coupon = (Coupon) session.getAttribute(SessionAttribute.COUPON);
        }
        if (coupon != null) {
            String codeName = coupon.getCodeName();
            try {
                boolean isDecreased = orderService.decreaseAvailableCouponAmount(codeName, 1);
                if (isDecreased) {
                    discount = orderService.findCouponDiscount(coupon.getCodeName());
                    coupon.setDiscount(discount);
                } else {
                    page = PageValue.CART_PAGE;
                    short newDiscount = 0;
                    session.setAttribute(SessionAttribute.COUPON_DISCOUNT, newDiscount);
                    session.removeAttribute(SessionAttribute.COUPON);
                    request.setAttribute(RequestParameter.COUPON_OUT, true);
                    return page;
                }
            } catch (ServiceException e) {
                request.setAttribute(RequestParameter.SERVER_ERROR, true);
                logger.log(Level.ERROR, e);
            }
        }
        try {
            User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
            boolean isOrderCreated = orderService.createOrder(user, cartMap, coupon);
            if (isOrderCreated) {
                request.setAttribute(RequestParameter.ORDER_CREATED, true);
                int cartAmount = 0;
                session.setAttribute(SessionAttribute.CART_AMOUNT, cartAmount);
                session.setAttribute(SessionAttribute.CART_MAP, new HashMap<Game, Integer>());
                short newDiscount = 0;
                session.setAttribute(SessionAttribute.COUPON_DISCOUNT, newDiscount);
                session.removeAttribute(SessionAttribute.COUPON);
                page = PageValue.INDEX;
            } else {
                session.setAttribute(SessionAttribute.CART_AMOUNT, orderService.countCartAmount(cartMap));
                request.setAttribute(RequestParameter.CART_AMOUNT_CHANGED, true);
            }
            if ((coupon != null) && !isOrderCreated) {
                orderService.increaseCouponAmount(coupon.getCodeName(), 1);
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
