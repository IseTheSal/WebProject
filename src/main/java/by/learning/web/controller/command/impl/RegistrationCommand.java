package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * <pre>Command provides users with role Guest registration.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.User
 */
public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.REGISTRATION_PAGE;
        String nameValue = request.getParameter(RequestParameter.FIRSTNAME);
        String lastnameValue = request.getParameter(RequestParameter.LASTNAME);
        String loginValue = request.getParameter(RequestParameter.LOGIN);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD);
        String repeatPasswordValue = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        String emailValue = request.getParameter(RequestParameter.EMAIL);
        try {
            Set<String> registrationInfo = userService.registerUser(nameValue, lastnameValue, loginValue, passwordValue, repeatPasswordValue, emailValue, User.Role.CLIENT);
            if (registrationInfo.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                logger.log(Level.INFO, "Successful registration");
                request.setAttribute(RequestParameter.REGISTRATION_COMPLETE, true);
                request.setAttribute(RequestParameter.SUCCESS, true);
                page = PageValue.LOGIN_PAGE;
            } else if (registrationInfo.remove(ValidationInformation.FAIL.getInfoValue())) {
                request.setAttribute(RequestParameter.REGISTRATION_FAIL, registrationInfo);
                request.setAttribute(RequestParameter.FAIL, true);
                page = PageValue.REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}