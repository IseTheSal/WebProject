package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService service;

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String nameValue = request.getParameter(RequestParameter.FIRSTNAME);
        String lastnameValue = request.getParameter(RequestParameter.LASTNAME);
        String loginValue = request.getParameter(RequestParameter.LOGIN);
        String passwordValue = request.getParameter(RequestParameter.PASSWORD);
        String repeatPasswordValue = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        String emailValue = request.getParameter(RequestParameter.EMAIL);
        boolean isRegister;
        try {
            isRegister = service.registerUser(nameValue, lastnameValue, loginValue, passwordValue, repeatPasswordValue, emailValue);
            if (isRegister) {
                logger.log(Level.INFO, "Successful registration");
                request.setAttribute(RequestParameter.REGISTRATION_COMPLETE, "Registration successfully complete");
                page = PagePath.LOGIN;
            } else {
                request.setAttribute(RequestParameter.REGISTRATION_FAIL, "Username or email already in use");
                page = PagePath.REGISTRATION;
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PagePath.REGISTRATION;
        }
        return page;
    }
}