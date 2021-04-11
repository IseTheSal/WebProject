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
 * <pre>Command sends reset password link by user`s email.</pre>
 *
 * @author Illia Aheyeu
 */
public class ForgotPasswordCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceInstance.INSTANCE.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.LOGIN_PAGE;
        String reestablishValue = request.getParameter(RequestParameter.REESTABLISH_VALUE);
        try {
            Set<String> info = userService.sentResetPasswordLink(reestablishValue);
            if (info.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                request.setAttribute(RequestParameter.CHECK_EMAIL, true);
            } else if (info.remove(ValidationInformation.FAIL.getInfoValue())) {
                request.setAttribute(RequestParameter.FAIL, true);
                request.setAttribute(RequestParameter.VALID_ISSUES, info);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
