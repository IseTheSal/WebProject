package by.learning.web.controller.filter;

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

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>Filter user to update actual information about games, user balance and cart changes.</pre>
 *
 * @author Illia Aheyeu
 */
public class UpdateValuesFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        updateGameList(req);
        checkGameChange(req);
        updateUserBalance(req);
        chain.doFilter(request, response);
    }

    /**
     * Update game list
     *
     * @param req HttpServletRequest request
     */
    private void updateGameList(HttpServletRequest req) {
        HttpSession session = req.getSession();
        GameService gameService = ServiceInstance.INSTANCE.getGameService();
        try {
            List<Game> allGames = gameService.findAllGames();
            session.setAttribute(SessionAttribute.GAME_LIST, allGames);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

    /**
     * Change cart games, in case if games were edited
     *
     * @param req HttpServletRequest request
     */
    private void checkGameChange(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        if (requestURI.equals(PageValue.CART_PAGE)) {
            HttpSession session = req.getSession();
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

    /**
     * Update user balance
     *
     * @param req HttpServletRequest request
     */
    private void updateUserBalance(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Object currUser = session.getAttribute(SessionAttribute.CURRENT_USER);
        if (currUser != null) {
            User user = (User) currUser;
            if (user.getRole() == User.Role.CLIENT) {
                try {
                    UserService userService = ServiceInstance.INSTANCE.getUserService();
                    BigDecimal userBalance = userService.findUserBalance(user.getId());
                    session.setAttribute(SessionAttribute.USER_BALANCE, userBalance);
                } catch (ServiceException e) {
                    logger.log(Level.ERROR, e);
                }
            }
        }
    }
}
