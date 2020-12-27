package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.manager.MessageManager;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.impl.UserServiceImpl;
import by.learning.web.validator.UserValidator;
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
        String loginValue = request.getParameter(RequestParameter.LOGIN_PARAM);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD_PARAM);
        boolean isLoginValid = UserValidator.isLoginValid(loginValue);
        boolean isPasswordValid = UserValidator.isPasswordValid(passwordValue);
        if (isLoginValid && isPasswordValid) {
            Optional<User> user = service.singIn(loginValue, passwordValue);
            if (user.isPresent()) {
                User currentUser = user.get();
                request.setAttribute(RequestParameter.USER_PARAM, currentUser);
                request.setAttribute(RequestParameter.USER_NAME_PARAM, currentUser.getName());
                request.setAttribute(RequestParameter.USER_PASSWORD_PARAM, currentUser.getPassword());
                page = PagePath.MAIN_PAGE;
            } else {
                request.setAttribute("errorSingInMessage", MessageManager.BY.getMessage("message.incorrectLoginOrPass"));
                page = PagePath.LOGIN;
            }
        } else {
            request.setAttribute("errorSingInMessage", MessageManager.BY.getMessage("message.incorrectData"));
            page = PagePath.LOGIN;
        }
        return page;
    }
}
