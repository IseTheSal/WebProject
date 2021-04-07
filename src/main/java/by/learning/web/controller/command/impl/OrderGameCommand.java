package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <pre>Command provides specific ordering to game list.</pre>
 *
 * @author Illia Aheyeu
 */
public class OrderGameCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private GameService gameService;

    public OrderGameCommand(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.MAIN_PAGE;
        String sortFilter = request.getParameter(RequestParameter.SORT_FILTER);
        HttpSession session = request.getSession();
        List<Game> gameList = (List<Game>) session.getAttribute(SessionAttribute.GAME_LIST);
        gameService.orderGameList(gameList, sortFilter);
        session.setAttribute(SessionAttribute.SORT_FILTER, sortFilter);
        return page;
    }
}
