package by.learning.web.controller.listener;

import by.learning.web.controller.attribute.SessionAttribute;
import by.learning.web.model.entity.Game;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.CART_MAP, new HashMap<Game, Integer>());
        session.setAttribute(SessionAttribute.CART_AMOUNT, 0);
        short discount = 0;
        session.setAttribute(SessionAttribute.COUPON_DISCOUNT, discount);
        logger.log(Level.DEBUG, "Listener worked");
    }
}
