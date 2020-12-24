package by.learning.web.controller;

import by.learning.web.command.ActionCommand;
import by.learning.web.command.CommandProvider;
import by.learning.web.command.PagePath;
import by.learning.web.command.impl.LogoutCommand;
import by.learning.web.manager.MessageManager;
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

@WebServlet(urlPatterns = {"/controller", "*.by"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);
    private final static String COMMAND_PARAM = "command";
    private final static String NULL_PAGE = "nullPage";

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
        Optional<ActionCommand> commandOptional = CommandProvider.defineCommand(commandValue);
        ActionCommand command = commandOptional.orElseThrow();
        String page = command.execute(req);
        if (page != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } else {
            req.getSession().setAttribute(NULL_PAGE, MessageManager.EN.getMessage("message.incorrectData"));
            resp.sendRedirect(req.getContextPath() + PagePath.INDEX);
        }
    }
}
