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
import java.util.Arrays;
import java.util.List;

/**
 * <pre>Filter games by specified parameters.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class GameFilterCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String DELIMITER = "\s";

    private GameService gameService;

    public GameFilterCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.MAIN_PAGE;
        String minPrice = request.getParameter(RequestParameter.MIN_PRICE);
        String maxPrice = request.getParameter(RequestParameter.MAX_PRICE);
        String category = request.getParameter(RequestParameter.CATEGORY_FILTER);
        String[] categories = (category == null) ? new String[]{} : category.split(DELIMITER);
        String genre = request.getParameter(RequestParameter.GENRE_FILTER);
        String[] genres = genre == null ? new String[]{} : genre.split(DELIMITER);
        try {
            List<Game> gameList = gameService.filterAllGames(Integer.parseInt(minPrice), Integer.parseInt(maxPrice), categories, genres);
            HttpSession session = request.getSession();
            session.setAttribute(SessionAttribute.GAME_LIST, gameList);
            request.setAttribute(RequestParameter.MIN_PRICE, minPrice);
            request.setAttribute(RequestParameter.MAX_PRICE, maxPrice);
            request.setAttribute(RequestParameter.CATEGORY_FILTER, Arrays.asList(categories));
            request.setAttribute(RequestParameter.GENRE_FILTER, Arrays.asList(genres));
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
