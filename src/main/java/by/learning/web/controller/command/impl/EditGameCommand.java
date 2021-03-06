package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.GameService;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class EditGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public EditGameCommand(GameService gameService) {
        this.gameService = gameService;
    }


    //fixme
    // save changes
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.ADMIN_GAME_LIST_PAGE;
        String gameIdValue = request.getParameter(RequestParameter.GAME_ID);
        String title = request.getParameter(RequestParameter.GAME_TITLE);
        String imagePath = request.getParameter(RequestParameter.GAME_IMAGE_PATH);
        //fixme
        if (imagePath.isBlank()) {
            imagePath = request.getParameter(RequestParameter.GAME_IMAGE_PATH_COPY);
        }
        String description = request.getParameter(RequestParameter.GAME_DESCRIPTION);
        String price = request.getParameter(RequestParameter.GAME_PRICE);
        String trailerLink = request.getParameter(RequestParameter.GAME_TRAILER_LINK);
        String[] categories = request.getParameterValues(RequestParameter.GAME_CATEGORIES);
        String[] genres = request.getParameterValues(RequestParameter.GAME_GENRES);
        try {
            Set<String> editInfo = gameService.editGame(gameIdValue, title, imagePath, description, price, trailerLink, genres, categories);
            if (editInfo.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                request.setAttribute(RequestParameter.SUCCESS, true);
            } else if (editInfo.remove(ValidationInformation.FAIL.getInfoValue())) {
                logger.log(Level.DEBUG, editInfo.toString());
                request.setAttribute(RequestParameter.VALID_ISSUES, editInfo);
                request.setAttribute(RequestParameter.FAIL, true);
            }
            request.getSession().removeAttribute(SessionAttribute.CURRENT_GAME);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.FAIL, true);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
