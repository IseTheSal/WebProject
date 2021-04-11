package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <pre>Increase client money balance.</pre>
 *
 * @author Illia Aheyeu
 */
public class IncreaseBalanceCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceInstance.INSTANCE.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String money = request.getParameter(RequestParameter.MONEY_AMOUNT);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        int id = user.getId();
        try {
            boolean isIncreased = userService.increaseUserBalance(id, money);
            if (isIncreased) {
                request.setAttribute(RequestParameter.SUCCESS, true);
                BigDecimal userBalance = userService.findUserBalance(id);
                session.setAttribute(SessionAttribute.USER_BALANCE, userBalance.setScale(2, RoundingMode.FLOOR));
            } else {
                request.setAttribute(RequestParameter.FAIL, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
