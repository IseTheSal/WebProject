package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>Command allows users with role Admin find gamecode amount of existing game.</pre>
 *
 * @author Illia Aheyeu
 * @see by.learning.web.model.entity.Game
 */
public class FindGameCodeAmount implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public FindGameCodeAmount(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_GAME_LIST_PAGE;
        String gameIdString = request.getParameter(RequestParameter.GAME_ID);
        int gameId = Integer.parseInt(gameIdString);
        try {
            int codeAmount = orderService.findCodeAmount(gameId);
            request.setAttribute(RequestParameter.GAME_CODE_AMOUNT, codeAmount);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
