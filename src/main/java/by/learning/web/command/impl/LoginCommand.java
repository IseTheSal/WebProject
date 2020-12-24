package by.learning.web.command.impl;

import by.learning.web.command.ActionCommand;
import by.learning.web.command.PagePath;
import by.learning.web.manager.MessageManager;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.impl.UserServiceImpl;
import by.learning.web.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LoginCommand implements ActionCommand {

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String USER_PARAM = "user";
    private final static String USER_NAME_PARAM = "userName";
    private final static String USER_PASSWORD_PARAM = "userPassword";

    private UserServiceImpl service;

    public LoginCommand(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(LOGIN_PARAM);
        String passwordValue = request.getParameter(PASSWORD_PARAM);
        boolean isLoginValid = UserValidator.isLoginValid(loginValue);
        boolean isPasswordValid = UserValidator.isPasswordValid(passwordValue);
        if (isLoginValid && isPasswordValid) {
            Optional<User> user = service.singIn(loginValue, passwordValue);
            if (user.isPresent()) {
                User currentUser = user.get();
                request.setAttribute(USER_PARAM, currentUser);
                request.setAttribute(USER_NAME_PARAM, currentUser.getName());
                request.setAttribute(USER_PASSWORD_PARAM, currentUser.getPassword());
                page = PagePath.REMOVE_USER;
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
