package by.learning.web.controller.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * <pre>Filter sets UTF-8 encoding for request and response.</pre>
 *
 * @author Illia Aheyeu
 */
public class CharacterSetFilter implements Filter {
    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding(ENCODING);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(ENCODING);
        next.doFilter(request, response);
    }
}
