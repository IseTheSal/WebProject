package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import by.learning.web.model.service.impl.ServiceInstance;
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
public class FilterGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String DELIMITER = "\s";

    private final GameService gameService = ServiceInstance.INSTANCE.getGameService();

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
            HttpSession session = request.getSession();
            Object sortFilter = session.getAttribute(SessionAttribute.SORT_FILTER);
            String sortValue = "";
            if (sortFilter != null) {
                sortValue = (String) sortFilter;
            }
            List<Game> gameList = gameService.filterAllGames(Integer.parseInt(minPrice), Integer.parseInt(maxPrice), categories, genres, sortValue);
            session.setAttribute(SessionAttribute.GAME_LIST, gameList);
            session.setAttribute(SessionAttribute.MIN_PRICE, minPrice);
            session.setAttribute(SessionAttribute.MAX_PRICE, maxPrice);
            session.setAttribute(SessionAttribute.CATEGORY_FILTER, Arrays.asList(categories));
            session.setAttribute(SessionAttribute.GENRE_FILTER, Arrays.asList(genres));
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
