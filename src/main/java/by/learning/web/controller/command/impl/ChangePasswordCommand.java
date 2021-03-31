package by.learning.web.controller.command.impl;

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

/**
 * <pre>Command allows users with roles Admin or Client change their passwords.</pre>
 *
 * @author Illia Aheyeu
 */
public class ChangePasswordCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public ChangePasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String oldPassword = request.getParameter(RequestParameter.OLD_PASSWORD);
        String newPassword = request.getParameter(RequestParameter.PASSWORD);
        String newPasswordRepeat = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        int userId = user.getId();
        try {
            boolean isChanged = userService.changeUserPassword(userId, oldPassword, newPassword, newPasswordRepeat);
            if (isChanged) {
                request.setAttribute(RequestParameter.SUCCESS, true);
            } else {
                request.setAttribute(RequestParameter.INCORRECT_OLD_PASSWORD, true);
                request.setAttribute(RequestParameter.FAIL, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
