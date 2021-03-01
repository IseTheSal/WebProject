package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
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
        String page = PagePath.PROFILE_PAGE;
        String email = request.getParameter(RequestParameter.EMAIL);
        String repeatEmail = request.getParameter(RequestParameter.EMAIL_REPEAT);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        int userId = user.getId();
        try {
            boolean isChanged = userService.changeEmail(userId, email, repeatEmail);
            if (isChanged) {
                request.setAttribute(RequestParameter.SUCCESS, true);
                user.setEmail(email);
                session.setAttribute(SessionAttribute.CURRENT_USER, user);
            } else {
                request.setAttribute(RequestParameter.EMAIL_EXIST, true);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
