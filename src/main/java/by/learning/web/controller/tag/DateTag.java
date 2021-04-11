package by.learning.web.controller.tag;

import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.model.entity.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

/**
 * <pre>Custom tag class.</pre>
 * Used to display current {@link LocalDate} for users with role Admin.
 *
 * @author Illia Aheyeu
 * @see User.Role
 */
public class DateTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user.getRole() == User.Role.ADMIN) {
            try {
                JspWriter out = pageContext.getOut();
                out.write("<label>" + user.getRole() + ".\s" + LocalDate.now() + "</label>");
            } catch (IOException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return SKIP_BODY;
    }
}
