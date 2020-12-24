package by.learning.web.command.impl;

import by.learning.web.command.ActionCommand;
import by.learning.web.command.PagePath;
import by.learning.web.manager.MessageManager;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.impl.UserServiceImpl;
import by.learning.web.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    private final static String LOGIN_PARAM = "login";
    private final static String PASSWORD_PARAM = "password";
    private final static String NAME_PARAM = "name";
    private final static String MESSAGE_ATTRIBUTE = "errorRegistrationMessage";
    private final static String MESSAGE_MANAGER_VALUE = "message.incorrectData";

    private UserServiceImpl service;

    public RegistrationCommand(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(LOGIN_PARAM);
        String passwordValue = request.getParameter(PASSWORD_PARAM);
        String nameValue = request.getParameter(NAME_PARAM);
        boolean isLoginValid = UserValidator.isLoginValid(loginValue);
        boolean isPasswordValid = UserValidator.isPasswordValid(passwordValue);
        boolean isNameValid = UserValidator.isNameValid(NAME_PARAM);
        if (isNameValid && isLoginValid && isPasswordValid &&
                (!service.singIn(loginValue, passwordValue).isPresent())) {
            service.addUser(new User(nameValue, loginValue, passwordValue));
            logger.log(Level.INFO, "User was added");
            page = PagePath.LOGIN;
        } else {
            request.setAttribute(MESSAGE_ATTRIBUTE, MessageManager.EN.getMessage(MESSAGE_MANAGER_VALUE));
            page = PagePath.REGISTRATION;
        }
        return page;
    }
}