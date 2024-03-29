package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.UserService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>Command allows users with role Guest open reset password page.</pre>
 *
 * @author Illia Aheyeu
 */
public class OpenResetPasswordCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceInstance.INSTANCE.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.LOGIN_PAGE;
        String resetToken = request.getParameter(RequestParameter.RESET_TOKEN);
        try {
            boolean tokenExist = userService.isTokenExist(resetToken);
            if (tokenExist) {
                page = PageValue.FORGOT_PASSWORD_PAGE;
                request.setAttribute(RequestParameter.RESET_TOKEN, resetToken);
            } else {
                request.setAttribute(RequestParameter.TOKEN_NOT_EXIST, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
