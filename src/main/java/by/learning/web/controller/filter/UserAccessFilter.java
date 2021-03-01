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

public class UserAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user == null) {
            logger.log(Level.INFO, "user is null");
            request.setAttribute(RequestParameter.NEED_AUTHORIZATION_FIRST, true);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PagePath.LOGIN_PAGE);
            requestDispatcher.forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }
}
