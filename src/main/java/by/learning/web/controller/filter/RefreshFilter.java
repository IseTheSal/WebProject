package by.learning.web.controller.filter;

import by.learning.web.controller.RequestParameter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class RefreshFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig fg) throws ServletException {
    }

    private HttpSession session = null;
    private HttpServletRequest httpServletRequest = null;
    private int serverToken = 0;
    private int clientToken = 0;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        httpServletRequest = (HttpServletRequest) req;
        session = httpServletRequest.getSession(true);
        if (httpServletRequest.getMethod().equals("GET")) {
            session.setAttribute("serverToken", new Random().nextInt(10000));
            chain.doFilter(req, res);
        } else {
            try {
                serverToken = (Integer) session.getAttribute("serverToken");
                clientToken = Integer.parseInt(req.getParameter("clientToken"));
            } catch (Exception e) {
                logger.log(Level.ERROR, e);
            }
            if (serverToken == clientToken) {
                session.setAttribute("serverToken", new Random().nextInt(10000));
                chain.doFilter(req, res);
            } else {
                String page = httpServletRequest.getParameter(RequestParameter.CURRENT_PAGE);
                RequestDispatcher rd = req.getRequestDispatcher(page);
                rd.forward(req, res);
            }
        }
    }

    @Override
    public void destroy() {
    }
}

