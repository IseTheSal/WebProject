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
import java.util.Optional;

public class OpenGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService service;

    public OpenGameCommand(GameService service) {
        this.service = service;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String idString = request.getParameter(RequestParameter.GAME_ID);
        int id = Integer.parseInt(idString);

        try {
            Optional<Game> gameById = service.findGameById(id);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                HttpSession session = request.getSession();
                session.setAttribute(SessionAttribute.CURRENT_GAME, game);
                page = PagePath.GAME_PAGE;
            } else {
                logger.log(Level.INFO, "Game with id {} not found", id);
                page = PagePath.INDEX;
            }
        } catch (ServiceException e) {
            logger.log(Level.INFO, e);
            page = PagePath.PAGE_SERVER_ERROR;
        }
        return page;
    }
}
