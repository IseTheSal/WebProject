package by.learning.web.controller.filter;

import by.learning.web.controller.PagePath;
import by.learning.web.controller.RequestParameter;
import by.learning.web.controller.SessionAttribute;
import by.learning.web.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class PageAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    private Set<String> guestPageAccess;
    private Set<String> userPageAccess;
    private Set<String> adminPageAccess;
    private Set<String> commonPageAccess;

    private static final String START_URI = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commonPageAccess = Set.of(PagePath.INDEX,
                PagePath.MAIN_PAGE,
                PagePath.GAME_PAGE,
                PagePath.ABOUT_PAGE,
                PagePath.TERMS_PAGE);
        guestPageAccess = Set.of(PagePath.CART_PAGE,
                PagePath.LOGIN_PAGE,
                PagePath.REGISTRATION_PAGE);
        userPageAccess = Set.of(PagePath.PROFILE_PAGE,
                PagePath.CART_PAGE);
        adminPageAccess = Set.of(PagePath.PROFILE_PAGE,
                PagePath.ADMIN_MENU_PAGE,
                PagePath.CREATE_GAME_PAGE,
                PagePath.ADMIN_GAME_LIST_PAGE,
                PagePath.EDIT_GAME_PAGE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();
        if (requestURI.equals(START_URI) || commonPageAccess.stream().anyMatch(requestURI::contains)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        boolean guestConfirm = guestPageAccess.stream().anyMatch(requestURI::contains);
        if (guestConfirm && user == null) {
            chain.doFilter(request, response);
            return;
        }
        if (user == null) {
            logger.log(Level.INFO, "user is null");
            request.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.LOGIN_PAGE);
            requestDispatcher.forward(request, response);
            return;
        }
        User.Role role = user.getRole();
        boolean userConfirm = userPageAccess.stream().anyMatch(requestURI::contains);
        boolean adminConfirm = adminPageAccess.stream().anyMatch(requestURI::contains);
        if ((role == User.Role.CLIENT && userConfirm)
                || (role == User.Role.ADMIN && adminConfirm)) {
            chain.doFilter(request, response);
        } else if (userConfirm || adminConfirm || guestConfirm) {
            resp.sendError(403);
        } else {
            resp.sendError(404);
        }
    }
}
