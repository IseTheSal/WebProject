package by.learning.web.controller.filter;

import by.learning.web.controller.attribute.PageValue;
import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
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

/**
 * <pre>Filter checks possibility of page access for certain user`s role.</pre>
 *
 * @author Illia Aheyeu
 */
public class PageAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    //Page`s set for different user`s roles.
    private Set<String> guestPageAccess;
    private Set<String> userPageAccess;
    private Set<String> adminPageAccess;
    private Set<String> commonPageAccess;

    private static final String START_URI = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commonPageAccess = Set.of(PageValue.INDEX,
                PageValue.MAIN_PAGE,
                PageValue.GAME_PAGE,
                PageValue.ABOUT_PAGE,
                PageValue.TERMS_PAGE);
        guestPageAccess = Set.of(PageValue.CART_PAGE,
                PageValue.LOGIN_PAGE,
                PageValue.REGISTRATION_PAGE);
        userPageAccess = Set.of(PageValue.PROFILE_PAGE,
                PageValue.CART_PAGE);
        adminPageAccess = Set.of(PageValue.PROFILE_PAGE,
                PageValue.ADMIN_MENU_PAGE,
                PageValue.CREATE_GAME_PAGE,
                PageValue.ADMIN_GAME_LIST_PAGE,
                PageValue.ADMIN_COUPON_LIST_PAGE,
                PageValue.ADMIN_ORDER_LIST_PAGE,
                PageValue.ADMIN_USER_LIST_PAGE);
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
        boolean userConfirm = userPageAccess.stream().anyMatch(requestURI::contains);
        boolean adminConfirm = adminPageAccess.stream().anyMatch(requestURI::contains);
        if (user == null && (userConfirm || adminConfirm)) {
            logger.log(Level.INFO, "user is null");
            request.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PageValue.LOGIN_PAGE);
            requestDispatcher.forward(request, response);
            return;
        } else if (!userConfirm && !adminConfirm) {
            resp.sendError(404);
            return;
        }

        User.Role role = user.getRole();
        if ((role == User.Role.CLIENT && userConfirm)
                || (role == User.Role.ADMIN && adminConfirm)) {
            chain.doFilter(request, response);
        } else {
            resp.sendError(403);
        }
    }
}
