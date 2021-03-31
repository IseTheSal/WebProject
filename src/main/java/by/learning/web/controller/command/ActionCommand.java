package by.learning.web.controller.command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>Main "Command" pattern interface.</pre>
 *
 * @author Illia Aheyeu
 */
public interface ActionCommand {
    /**
     * @param request  Used to set/get values to/from HttpServletRequest.
     * @param response Used to print information without forward/redirect.
     * @return Page to forward.
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
