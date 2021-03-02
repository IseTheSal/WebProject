package by.learning.web.controller;

import by.learning.web.controller.command.ActionCommand;
import by.learning.web.controller.command.CommandProvider;
import by.learning.web.exception.ConnectionPoolException;
import by.learning.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/controller", "*.do"}, name = "controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMAND_PARAM = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandValue = req.getParameter(COMMAND_PARAM);
        logger.log(Level.DEBUG, "Command - " + commandValue);
        Optional<ActionCommand> commandOptional = CommandProvider.defineCommand(commandValue);
        ActionCommand command = commandOptional.orElseThrow();
        String page = command.execute(req, resp);
        logger.log(Level.DEBUG, "to page - " + page);
        if (page != null) {
            if (!page.equals(PagePath.UPLOAD_VALUE)) {
                RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }
        } else {
            page = PagePath.PAGE_NOT_FOUND;
            resp.sendRedirect(req.getContextPath() + page);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool.INSTANCE.destroyPool();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, e);
        }
    }
}
