package by.learning.web.controller.command.impl;

import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.impl.GameServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

public class AddToCartCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private GameServiceImpl gameService;

    public AddToCartCommand(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = request.getParameter(RequestParameter.CURRENT_PAGE);
        String gameId = request.getParameter(RequestParameter.GAME_ID);
        int id = Integer.parseInt(gameId);
        try {
            boolean gameInStock = gameService.isGameInStock(id);
            if (gameInStock) {
                HttpSession session = request.getSession();
                ArrayList<Game> cartList = (ArrayList<Game>) session.getAttribute(SessionAttribute.CART_LIST);
                Game game = (Game) session.getAttribute(SessionAttribute.CURRENT_GAME);
                cartList.add(game);
                session.setAttribute(SessionAttribute.CART_LIST, cartList);
            } else {
                request.setAttribute(RequestParameter.GAME_IN_STOCK, false);
            }
        } catch (ServiceException e) {
            logger.log(Level.WARN, e);
        }
        return page;
    }
}
