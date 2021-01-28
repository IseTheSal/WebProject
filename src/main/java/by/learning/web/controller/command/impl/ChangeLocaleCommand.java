package by.learning.web.controller.command.impl;

import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLocaleCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String locale = request.getParameter(RequestParameter.CURRENT_LOCALE);
        HttpSession session = request.getSession();
        if (locale != null) {
            session.setAttribute(SessionAttribute.CURRENT_LOCALE, locale);
        } else {
            logger.log(Level.INFO, "Locale is null");
        }
        return page;
    }
}
