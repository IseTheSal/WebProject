package by.learning.web.controller.command.impl;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.GameService;
import by.learning.web.util.CustomFileReader;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CreateGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public CreateGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PagePath.ADMIN_MENU_PAGE;
        String gameTitle = request.getParameter(RequestParameter.GAME_TITLE);
        String imagePath = request.getParameter(RequestParameter.GAME_IMAGE_PATH);
        String description = request.getParameter(RequestParameter.GAME_DESCRIPTION);
        String price = request.getParameter(RequestParameter.GAME_PRICE);
        String trailerLink = request.getParameter(RequestParameter.GAME_TRAILER_LINK);
        String[] genres = request.getParameterValues(RequestParameter.GAME_GENRES);
        String[] categories = request.getParameterValues(RequestParameter.GAME_CATEGORIES);

        try {
            Set<String> informationSet = gameService.createGame(gameTitle, imagePath, description, price, trailerLink, genres, categories);
            if (informationSet.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                if (!imagePath.isEmpty()) {
                    try (ServletOutputStream outputStream = response.getOutputStream()) {
                        response.sendRedirect(request.getContextPath() + page);
                        CustomFileReader customFileReader = CustomFileReader.getInstance();
                        outputStream.write(customFileReader.readAndWriteImage(imagePath));
                        page = PagePath.UPLOAD_VALUE;
                        return page;
                    } catch (IOException | ServiceException e) {
                        logger.log(Level.ERROR, e);
                    }
                }
            } else if (informationSet.remove(ValidationInformation.FAIL.getInfoValue())) {
                logger.log(Level.DEBUG, informationSet.toString());
                request.setAttribute(RequestParameter.VALID_ISSUES, informationSet);
                request.setAttribute(RequestParameter.FAIL, true);
                page = request.getParameter(RequestParameter.CURRENT_PAGE);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            page = request.getParameter(RequestParameter.CURRENT_PAGE);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
