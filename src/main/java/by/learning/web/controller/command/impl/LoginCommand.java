package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger(LoginCommand.class);

    private UserServiceImpl service;

    public LoginCommand(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        logger.log(Level.INFO, "here");
        String loginValue = request.getParameter(RequestParameter.LOGIN);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD);
        Optional<User> user;
        try {
            user = service.singIn(loginValue, passwordValue);
            if (user.isPresent()) {
                User currentUser = user.get();
                request.setAttribute(RequestParameter.USER_PARAM, currentUser);
                request.getSession().setAttribute(RequestParameter.FIRSTNAME, currentUser.getFirstname());
                request.setAttribute(RequestParameter.USER_PASSWORD, currentUser.getPassword());
                page = PagePath.MAIN_PAGE;
            } else {
                request.setAttribute(RequestParameter.ERROR_SING_IN, "Incorrect login or password");
                page = PagePath.LOGIN;
                logger.log(Level.INFO, "Error in logging");
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.ERROR_SING_IN, "Incorrect login or password");
            page = PagePath.LOGIN;
        }
        return page;
    }
}
