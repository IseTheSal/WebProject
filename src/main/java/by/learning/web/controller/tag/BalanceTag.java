package by.learning.web.controller.tag;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.GameService;
import by.learning.web.model.service.UserService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

public class BalanceTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user.getRole() == User.Role.CLIENT) {
            checkGameChange(session);
            try {
                UserService userService = ServiceInstance.INSTANCE.getUserService();
                BigDecimal userBalance = userService.findUserBalance(user.getId());
                JspWriter out = pageContext.getOut();
                out.write("<a style=\"text-decoration: none; color: white; margin-right: 30px; cursor: pointer\" id=\"shopMap\"\n" +
                        "                   class=\"\" onclick=\"$('#balanceButton').click();\">\n" +
                        "                    <i style=\"margin-right: 5px\"\n" +
                        "                       class=\"fas fa-plus-circle\"></i>" + userBalance.setScale(2, RoundingMode.FLOOR)
                        + "$ </a > ");
            } catch (ServiceException | IOException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return SKIP_BODY;
    }

    private void checkGameChange(HttpSession session) {
        ServletRequest request = pageContext.getRequest();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.equals(PageValue.CART_PAGE)) {
            HashMap<Game, Integer> cartMap = (HashMap<Game, Integer>) session.getAttribute(SessionAttribute.CART_MAP);
            GameService gameService = ServiceInstance.INSTANCE.getGameService();
            try {
                Set<Game> games = cartMap.keySet();
                for (Game game : games) {
                    int gameId = game.getId();
                    Optional<Game> gameById = gameService.findGameById(gameId);
                    if (gameById.isPresent() && (!gameById.get().equals(game))) {
                        Integer amount = cartMap.get(game);
                        cartMap.remove(game);
                        cartMap.put(gameById.get(), amount);
                    }
                }
            } catch (ServiceException e) {
                logger.log(Level.ERROR, e);
            }
        }
    }


}
