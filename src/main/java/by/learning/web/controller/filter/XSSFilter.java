package by.learning.web.controller.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Filter prevents cross site scripting attack
 *
 * @author Illia Aheyeu
 */
public class XSSFilter implements Filter {

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
                            .replaceAll("\"", "&quot;")
                            .replaceAll("'", "&#x27;")
                            .replaceAll("&", "&amp;")
                            .replaceAll("/", "&#x2F;")
                            .replaceAll("\\(", "&#40;")
                            .replaceAll("\\)", "&#41;")
                            .replaceAll(":", "&#58;");
                }
            }
            request.setAttribute(paramName, paramValues);
        }
        chain.doFilter(request, response);
    }
}
