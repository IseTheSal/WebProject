package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.GameService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class OpenGameCreatorCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public OpenGameCreatorCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page;
        try {
            HashMap<Integer, String> gameGenres = gameService.findAllGenres();
            HashMap<Integer, String> gameCategories = gameService.findAllCategories();
            HttpSession session = request.getSession();
            if (gameCategories.isEmpty() || gameGenres.isEmpty()) {
                request.setAttribute(RequestParameter.FAIL, true);
            }
            session.setAttribute(SessionAttribute.GENRES_MAP, gameGenres);
            session.setAttribute(SessionAttribute.CATEGORIES_MAP, gameCategories);
            page = PagePath.CREATE_GAME_PAGE;
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
            page = PagePath.ADMIN_MENU_PAGE;
        }
        return page;
    }
}
