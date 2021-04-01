package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
import by.learning.web.controller.command.ActionCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <pre>Command provides users with roles Admin or Client logout and redirect to login page</pre>
 *
 * @author Illia Aheyeu
 */
public class LogoutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.UPLOAD_VALUE;
        HttpSession session = request.getSession();
        session.invalidate();
        logger.log(Level.INFO, "Session was invalidate");
        try {
            response.sendRedirect(PagePath.LOGIN_PAGE);
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
        return page;
    }
}
