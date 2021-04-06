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
import java.util.Optional;

/**
 * <pre>Command allows users with all roles open {@link by.learning.web.model.entity.Game game} page.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class OpenGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService service;

    public OpenGameCommand(GameService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        String idString = request.getParameter(RequestParameter.GAME_ID);
        int id = Integer.parseInt(idString);

        try {
            Optional<Game> gameById = service.findGameById(id);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.CURRENT_GAME, game);
                page = PageValue.GAME_PAGE;
            } else {
                logger.log(Level.INFO, "Game with id {} not found", id);
                request.setAttribute(RequestParameter.FAIL, true);
                page = PageValue.INDEX;
            }
        } catch (ServiceException e) {
            logger.log(Level.INFO, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PageValue.SERVER_ERROR_PAGE;
        }
        return page;
    }
}
