package by.learning.web.controller;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.GameService;
import by.learning.web.model.service.impl.ServiceInstance;
import by.learning.web.validator.ValidationInformation;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Set;

/**
 * <pre>File upload controller witch process every admin`s request with '/createGame.upload'.</pre>
 *
 * @author Illia Aheyeu
 */
@WebServlet(name = "gameImageUpload", urlPatterns = {"/createGame.upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100)
public class UploadController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user == null) {
            req.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            page = PageValue.LOGIN_PAGE;
            req.getRequestDispatcher(page).forward(req, resp);
        } else if (user.getRole() == User.Role.ADMIN) {
            req.setAttribute(RequestParameter.FAIL, true);
            page = PageValue.ADMIN_MENU_PAGE;
            req.getRequestDispatcher(page).forward(req, resp);
        } else {
            resp.sendError(403);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String page = PageValue.CREATE_GAME_PAGE;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        boolean successOperation = false;
        boolean error = false;
        if ((user != null) && (user.getRole() == User.Role.ADMIN)
                && ServletFileUpload.isMultipartContent(request)) {
            String gameTitle = request.getParameter(RequestParameter.GAME_TITLE);
            String description = request.getParameter(RequestParameter.GAME_DESCRIPTION);
            String price = request.getParameter(RequestParameter.GAME_PRICE);
            String trailerLink = request.getParameter(RequestParameter.GAME_TRAILER_LINK);
            String[] genres = request.getParameterValues(RequestParameter.GAME_GENRES);
            String[] categories = request.getParameterValues(RequestParameter.GAME_CATEGORIES);
            Part imagePart = request.getPart(RequestParameter.GAME_IMAGE_PATH);
            if (!imagePart.getSubmittedFileName().isBlank()) {
                try {
                    GameService gameService = ServiceInstance.INSTANCE.getGameService();
                    Set<String> informationSet = gameService.createGame(gameTitle, imagePart, description, price, trailerLink, genres, categories);
                    if (informationSet.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                        request.setAttribute(RequestParameter.SUCCESS, true);
                        page = PageValue.ADMIN_MENU_PAGE;
                        successOperation = true;
                    } else if (informationSet.remove(ValidationInformation.FAIL.getInfoValue())) {
                        request.setAttribute(RequestParameter.VALID_ISSUES, informationSet);
                        request.setAttribute(RequestParameter.FAIL, true);
                    }
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                    request.setAttribute(RequestParameter.SERVER_ERROR, true);
                }
            }
        } else if (user == null) {
            request.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            page = PageValue.LOGIN_PAGE;
        } else {
            response.sendError(403);
            error = true;
        }
        if (!error) {
            if (successOperation) {
                response.sendRedirect(page);
            } else {
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
    }
}
