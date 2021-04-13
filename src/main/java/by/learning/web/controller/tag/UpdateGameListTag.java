package by.learning.web.controller.tag;

import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class UpdateGameListTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        GameService gameService = ServiceInstance.INSTANCE.getGameService();
        try {
            List<Game> allGames = gameService.findAllGames();
            session.setAttribute(SessionAttribute.GAME_LIST, allGames);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
        return SKIP_BODY;
    }
}
