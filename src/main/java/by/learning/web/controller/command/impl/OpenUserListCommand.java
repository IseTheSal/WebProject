package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
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
import java.util.Set;

/**
 * <pre>Command allows users with role Admin find all {@link by.learning.web.model.entity.User users}.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class OpenUserListCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public OpenUserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_USER_LIST_PAGE;
        try {
            Set<User> allUsers = userService.findAllUsers();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.USER_SET, allUsers);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
