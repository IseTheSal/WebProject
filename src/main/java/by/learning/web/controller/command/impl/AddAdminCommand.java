package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
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
 * <pre>Command allows users with Admin role add new admin.</pre>
 *
 * @author Illia Aheyeu
 */
public class AddAdminCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private final UserService userService = ServiceInstance.INSTANCE.getUserService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_MENU_PAGE;
        String firstName = request.getParameter(RequestParameter.FIRSTNAME);
        String lastName = request.getParameter(RequestParameter.LASTNAME);
        String login = request.getParameter(RequestParameter.LOGIN);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String repeatPassword = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        String email = request.getParameter(RequestParameter.EMAIL);
        try {
            Set<String> registrationInfo = userService.registerUser(firstName, lastName, login, password, repeatPassword, email, User.Role.ADMIN);
            if (registrationInfo.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                request.setAttribute(RequestParameter.SUCCESS, true);
            } else if (registrationInfo.remove(ValidationInformation.FAIL.getInfoValue())) {
                request.setAttribute(RequestParameter.REGISTRATION_FAIL, registrationInfo);
                request.setAttribute(RequestParameter.FAIL, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
