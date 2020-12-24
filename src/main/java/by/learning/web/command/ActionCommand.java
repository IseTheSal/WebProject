package by.learning.web.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    String execute(HttpServletRequest request);
}
