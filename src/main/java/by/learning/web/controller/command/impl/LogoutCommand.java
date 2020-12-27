package by.learning.web.controller.command.impl;

import by.learning.web.controller.command.ActionCommand;
import by.learning.web.controller.PagePath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.LOGIN;
        HttpSession session = request.getSession();
        session.invalidate();
        logger.log(Level.INFO, "Session was invalidate");
        return page;
    }
}
