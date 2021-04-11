package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.UserService;
import by.learning.web.model.service.impl.ServiceInstance;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * <pre>Command provides users with role Guest reset their passwords.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.User
 */
public class ResetPasswordCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceInstance.INSTANCE.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.LOGIN_PAGE;
        String resetToken = request.getParameter(RequestParameter.RESET_TOKEN);
        String newPassword = request.getParameter(RequestParameter.PASSWORD);
        String newPasswordRepeat = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        try {
            Set<String> info = userService.resetPassword(resetToken, newPassword, newPasswordRepeat);
            if (info.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                request.setAttribute(RequestParameter.PASSWORD_CHANGED, true);
            } else if (info.remove(ValidationInformation.FAIL.getInfoValue())) {
                request.setAttribute(RequestParameter.FAIL, true);
                if (info.contains(ValidationInformation.USER_NOT_FOUND.getInfoValue())) {
                    request.setAttribute(RequestParameter.TOKEN_NOT_EXIST, true);
                } else {
                    request.setAttribute(RequestParameter.RESET_TOKEN, resetToken);
                    request.setAttribute(RequestParameter.VALID_ISSUES, info);
                    page = PageValue.FORGOT_PASSWORD_PAGE;
                }
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
