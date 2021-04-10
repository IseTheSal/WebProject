package by.learning.web.controller.command.impl;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.command.ActionCommand;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.service.OrderService;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

/**
 * <pre>Command allows users with Admin role add gamecode of existing game</pre>
 *
 * @author Illia Aheyeu
 */
public class AddGameCodeCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private OrderService orderService;

    public AddGameCodeCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PageValue.ADMIN_MENU_PAGE;
        String gameId = request.getParameter(RequestParameter.GAME_ID);
        String gameCode = request.getParameter(RequestParameter.GAME_CODE).toUpperCase(Locale.ROOT);
        try {
            Set<String> validInfo = orderService.addGameCode(gameId, gameCode);
            if (validInfo.remove(ValidationInformation.SUCCESS.getInfoValue())) {
                request.setAttribute(RequestParameter.SUCCESS, true);
            } else if (validInfo.remove(ValidationInformation.FAIL.getInfoValue())) {
                request.setAttribute(RequestParameter.FAIL, true);
                request.setAttribute(RequestParameter.VALID_ISSUES, validInfo);
            }
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
            request.setAttribute(RequestParameter.CODE_EXISTS, true);
            request.setAttribute(RequestParameter.SERVER_ERROR, true);
        }
        return page;
    }
}
