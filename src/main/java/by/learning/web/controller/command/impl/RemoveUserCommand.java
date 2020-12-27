package by.learning.web.controller.command.impl;

import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.controller.PagePath;
import by.learning.web.manager.MessageManager;
import by.learning.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(RemoveUserCommand.class);

    private UserServiceImpl userService;

    public RemoveUserCommand(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String findingValue = request.getParameter(RequestParameter.USER_ID_PARAM);
        int userId = Integer.parseInt(findingValue);
        boolean isRemoved = userService.removeUserById(userId);
        if (isRemoved) {
            page = PagePath.LOGIN;
            logger.log(Level.INFO, "User was removed");
        } else {
            request.setAttribute(RequestParameter.MESSAGE_ATTRIBUTE, MessageManager.EN.getMessage(RequestParameter.MESSAGE_MANAGER_VALUE));
            page = PagePath.REMOVE_USER;
        }
        return page;
    }
}
