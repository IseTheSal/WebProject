package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class HomeCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService service;

    public HomeCommand(GameService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            List<Game> allGames = service.findAllGames();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.GAME_LIST, allGames);
            page = PagePath.MAIN_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PagePath.PAGE_SERVER_ERROR;
        }
        return page;
    }
}
