package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PagePath;
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
import java.util.HashMap;
import java.util.Optional;

/**
 * <pre>Command allows users with role Admin open {@link by.learning.web.model.entity.Game game} editor page.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class OpenEditGameCommand implements ActionCommand {
    public static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public OpenEditGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String gameIdValue = request.getParameter(RequestParameter.GAME_ID);
        int gameId = Integer.parseInt(gameIdValue);
        Optional<Game> gameById;
        try {
            gameById = gameService.findGameById(gameId);
            if (gameById.isPresent()) {
                HttpSession session = request.getSession();
                Game game = gameById.get();
                session.setAttribute(SessionAttribute.CURRENT_GAME, game);
                HashMap<Integer, String> gameGenres = gameService.findGameGenres(gameId);
                session.setAttribute(SessionAttribute.GAME_GENRES_MAP, gameGenres);
                HashMap<Integer, String> gameCategories = gameService.findGameCategories(gameId);
                session.setAttribute(SessionAttribute.GAME_CATEGORIES_MAP, gameCategories);
                HashMap<Integer, String> allGenres = gameService.findAllGenres();
                session.setAttribute(SessionAttribute.GENRES_MAP, allGenres);
                HashMap<Integer, String> allCategories = gameService.findAllCategories();
                session.setAttribute(SessionAttribute.CATEGORIES_MAP, allCategories);
                page = PagePath.EDIT_GAME_PAGE;
            } else {
                logger.log(Level.INFO, "game doesnt exist");
                request.setAttribute(RequestParameter.FAIL, true);
                page = PagePath.ADMIN_GAME_LIST_PAGE;
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            page = PagePath.ADMIN_GAME_LIST_PAGE;
            request.setAttribute(RequestParameter.FAIL, true);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
