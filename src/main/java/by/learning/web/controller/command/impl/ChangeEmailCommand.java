package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
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

public class ChangeEmailCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public ChangeEmailCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String email = request.getParameter(RequestParameter.EMAIL);
        String repeatEmail = request.getParameter(RequestParameter.EMAIL_REPEAT);
        int userId = Integer.parseInt(request.getParameter(RequestParameter.USER_ID));
        try {
            boolean isChanged = userService.changeEmail(userId, email, repeatEmail);
            if (isChanged) {
                request.setAttribute(RequestParameter.SUCCESS, true);
            } else {
                request.setAttribute(RequestParameter.EMAIL_EXIST, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        page = (user.getRole() == User.Role.ADMIN) ? PagePath.ADMIN_USER_LIST_PAGE : PagePath.PROFILE_PAGE;
        return page;
    }
}
