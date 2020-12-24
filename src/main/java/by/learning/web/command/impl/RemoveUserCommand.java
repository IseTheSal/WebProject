package by.learning.web.command.impl;

import by.learning.web.command.ActionCommand;
import by.learning.web.command.PagePath;
import by.learning.web.manager.MessageManager;
import by.learning.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RemoveUserCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(RemoveUserCommand.class);

    private static final String USER_ID_PARAM = "userId";
    private final static String MESSAGE_ATTRIBUTE = "errorRemovingMessage";
    private final static String MESSAGE_MANAGER_VALUE = "message.incorrectData";

    private UserServiceImpl userService;

    public RemoveUserCommand(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String findingValue = request.getParameter(USER_ID_PARAM);
        int userId = Integer.parseInt(findingValue);
        boolean isRemoved = userService.removeUserById(userId);
        if (isRemoved) {
            page = PagePath.LOGIN;
            logger.log(Level.INFO, "User was removed");
        } else {
            request.setAttribute(MESSAGE_ATTRIBUTE, MessageManager.EN.getMessage(MESSAGE_MANAGER_VALUE));
            page = PagePath.REMOVE_USER;
        }
        return page;
    }
}
