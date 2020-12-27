package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
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


    private UserServiceImpl service;

    public RegistrationCommand(UserServiceImpl service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String loginValue = request.getParameter(RequestParameter.LOGIN_PARAM);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD_PARAM);
        String nameValue = request.getParameter(RequestParameter.NAME_PARAM);
        boolean isLoginValid = UserValidator.isLoginValid(loginValue);
        boolean isPasswordValid = UserValidator.isPasswordValid(passwordValue);
        boolean isNameValid = UserValidator.isNameValid(RequestParameter.NAME_PARAM);
        if (isNameValid && isLoginValid && isPasswordValid &&
                (service.singIn(loginValue, passwordValue).isEmpty())) {
            service.addUser(new User(nameValue, loginValue, passwordValue));
            logger.log(Level.INFO, "User was added");
            page = PagePath.LOGIN;
        } else {
            request.setAttribute(RequestParameter.MESSAGE_ATTRIBUTE, MessageManager.EN.getMessage(RequestParameter.MESSAGE_MANAGER_VALUE));
            page = PagePath.REGISTRATION;
        }
        return page;
    }
}