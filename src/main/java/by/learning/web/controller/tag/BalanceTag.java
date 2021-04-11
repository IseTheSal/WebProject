package by.learning.web.controller.tag;

import by.learning.web.controller.attribute.RequestParameter;
import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.model.service.impl.ServiceInstance;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BalanceTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.CURRENT_USER);
        if (user.getRole() == User.Role.CLIENT) {
            try {
                UserService userService = ServiceInstance.INSTANCE.getUserService();
                BigDecimal userBalance = userService.findUserBalance(user.getId());
                JspWriter out = pageContext.getOut();
                out.write("<a style=\"text-decoration: none; color: white; margin-right: 30px; cursor: pointer\" id=\"shopMap\"\n" +
                        "                   class=\"\" onclick=\"$('#balanceButton').click();\">\n" +
                        "                    <i style=\"margin-right: 5px\"\n" +
                        "                       class=\"fas fa-plus-circle\"></i>" + userBalance.setScale(2, RoundingMode.FLOOR)
                        + "$ </a > ");
            } catch (ServiceException | IOException e) {
                logger.log(Level.ERROR, e);
            }
        }
        return SKIP_BODY;
    }


}
