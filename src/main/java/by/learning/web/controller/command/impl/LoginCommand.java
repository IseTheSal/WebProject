package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * <pre>Command provides users with role Guest authentication and in case of success authorization.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.User
 */
public class LoginCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService service;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String loginValue = request.getParameter(RequestParameter.LOGIN);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD);
        Optional<User> user;
        try {
            user = service.singIn(loginValue, passwordValue);
            if (user.isPresent()) {
                User currentUser = user.get();
                HttpSession session = request.getSession(true);
                session.setAttribute(SessionAttribute.CURRENT_USER, currentUser);
                if (currentUser.getRole() == User.Role.ADMIN) {
                    session.removeAttribute(SessionAttribute.CART_MAP);
                    session.removeAttribute(SessionAttribute.CART_AMOUNT);
                    session.removeAttribute(SessionAttribute.COUPON_DISCOUNT);
                }
                page = PageValue.INDEX;
            } else {
                request.setAttribute(RequestParameter.ERROR_SING_IN, "Incorrect login or password");
                page = PageValue.LOGIN_PAGE;
                logger.log(Level.INFO, "Error in logging");
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PageValue.LOGIN_PAGE;
        }
        return page;
    }
}
