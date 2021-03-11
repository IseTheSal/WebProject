package by.learning.web.controller.filter;

import by.learning.web.controller.attribute.PagePath;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.controller.command.CommandType;
import by.learning.web.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

public class CommandAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String COMMAND_PARAMETER = "command";

    Set<CommandType> guestCommands;
    Set<CommandType> adminCommands;
    Set<CommandType> clientCommands;
    Set<CommandType> authorizedUserCommands;
    Set<CommandType> allCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestCommands = EnumSet.of(CommandType.ADD_TO_CART,
                CommandType.OPEN_GAME,
                CommandType.REMOVE_FROM_CART,
                CommandType.CHANGE_CART_AMOUNT,
                CommandType.USE_PROMOCODE,
                CommandType.LOGIN,
                CommandType.REGISTRATION,
                CommandType.CHANGE_LOCALE);
        adminCommands = EnumSet.of(CommandType.CREATE_GAME,
                CommandType.OPEN_GAME,
                CommandType.OPEN_GAME_CREATOR,
                CommandType.FIND_CODE_AMOUNT,
                CommandType.CHANGE_LOCALE,
                CommandType.ADD_GAME_CODE,
                CommandType.OPEN_GAME_EDITOR,
                CommandType.EDIT_GAME,
                CommandType.FIND_CODE_AMOUNT,
                CommandType.OPEN_COUPON_LIST,
                CommandType.CREATE_COUPON,
                CommandType.EDIT_COUPON_AMOUNT,
                CommandType.DELETE_COUPON,
                CommandType.OPEN_ORDER_LIST);
        clientCommands = EnumSet.of(CommandType.ADD_TO_CART,
                CommandType.OPEN_GAME,
                CommandType.REMOVE_FROM_CART,
                CommandType.CHANGE_CART_AMOUNT,
                CommandType.USE_PROMOCODE,
                CommandType.MAKE_ORDER,
                CommandType.CHANGE_LOCALE);
        authorizedUserCommands = EnumSet.of(CommandType.LOGOUT,
                CommandType.CHANGE_EMAIL,
                CommandType.CHANGE_PASSWORD,
                CommandType.FIND_ORDER_HISTORY);
        allCommands = EnumSet.allOf(CommandType.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        String commandValue = req.getParameter(COMMAND_PARAMETER);
        CommandType command = CommandType.valueOf(commandValue.toUpperCase(Locale.ROOT));
        if (guestCommands.contains(command) && user == null) {
            chain.doFilter(request, response);
            return;
        }
        if (user == null) {
            logger.log(Level.INFO, "user is null");
            req.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            RequestDispatcher requestDispatcher = req.getServletContext().getRequestDispatcher(PagePath.LOGIN_PAGE);
            requestDispatcher.forward(request, response);
            return;
        } else if (authorizedUserCommands.contains(command)) {
            chain.doFilter(request, response);
            return;
        }
        User.Role role = user.getRole();
        if ((role == User.Role.CLIENT && clientCommands.contains(command)) ||
                (role == User.Role.ADMIN && adminCommands.contains(command))) {
            chain.doFilter(request, response);
        } else if (allCommands.contains(command)) {
            resp.sendError(403);
        } else {
            resp.sendError(400);
        }
    }
}
