package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <pre>Command finds all games.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class HomeCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public HomeCommand(GameService service) {
        this.gameService = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        try {
            List<Game> allGames = gameService.findAllGames();
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.GAME_LIST, allGames);
            session.removeAttribute(SessionAttribute.CATEGORY_FILTER);
            session.removeAttribute(SessionAttribute.GENRE_FILTER);
            session.removeAttribute(SessionAttribute.MIN_PRICE);
            session.removeAttribute(SessionAttribute.MAX_PRICE);
            session.removeAttribute(SessionAttribute.SORT_FILTER);
            page = PageValue.MAIN_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PageValue.SERVER_ERROR_PAGE;
        }
        return page;
    }
}
