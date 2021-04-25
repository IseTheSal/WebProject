package by.learning.web.controller.filter;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

/**
 * Filter prevents double-post (refresh) actions
 *
 * @author Illia Aheyeu
 */
public class RefreshFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private static final String GET_METHOD = "GET";
    private HttpSession session = null;

    @Override
    public void init(FilterConfig fg) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        if (httpServletRequest.getMethod().equals(GET_METHOD)) {
            session = httpServletRequest.getSession(true);
            session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
            chain.doFilter(req, res);
        } else {
            try {
                int serverToken = (Integer) session.getAttribute(SessionAttribute.SERVER_TOKEN);
                int clientToken = Integer.parseInt(req.getParameter(RequestParameter.CLIENT_TOKEN));
                if (serverToken == clientToken) {
                    session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
                    chain.doFilter(req, res);
                } else {
                    String page = httpServletRequest.getParameter(RequestParameter.CURRENT_PAGE);
                    HttpServletResponse httpServletResponse = (HttpServletResponse) res;
                    httpServletResponse.sendRedirect(page);
                }
            } catch (IllegalStateException | NumberFormatException | NullPointerException ex) {
                logger.log(Level.ERROR, ex);
                HttpServletResponse httpServletResponse = (HttpServletResponse) res;
                String page = httpServletRequest.getParameter(RequestParameter.CURRENT_PAGE);
                httpServletResponse.sendRedirect(page);
            }
        }
    }

    @Override
    public void destroy() {
    }
}

