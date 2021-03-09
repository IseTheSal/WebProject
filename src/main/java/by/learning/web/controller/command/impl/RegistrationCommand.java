package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService service;

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
                request.setAttribute(RequestParameter.SUCCESS, true);
                page = PagePath.LOGIN_PAGE;
            } else {
                request.setAttribute(RequestParameter.REGISTRATION_FAIL, "Username or email already in use");
                page = PagePath.REGISTRATION_PAGE;
            }
        } catch (ServiceException e) {
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PagePath.REGISTRATION_PAGE;
        }
        return page;
    }
}