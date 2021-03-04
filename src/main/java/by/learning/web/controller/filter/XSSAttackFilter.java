package by.learning.web.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;


public class XSSAttackFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                if (paramValues[i] != null) {
                    paramValues[i] = paramValues[i].replaceAll("<", "&lt;")
                            .replaceAll(">", "&gt;")
                            .replaceAll("\"", "&quot")
                            .replaceAll("'", "&#x27")
                            .replaceAll("&", "&amp")
                            .replaceAll("/", "&#x2F");
                }
            }
            request.setAttribute(paramName, paramValues);
        }
        chain.doFilter(request, response);
    }
}
