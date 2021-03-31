package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <pre>Command allows users with all roles change language(PL, RU, EN).</pre>
 *
 * @author Illia Aheyeu
 */
public class ChangeLocaleCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
