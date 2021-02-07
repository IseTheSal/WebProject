package by.learning.web.controller.listener;

import by.learning.web.controller.SessionAttribute;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.Random;

public class SessionListener implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.CART_LIST, new ArrayList<>());
        logger.log(Level.DEBUG, "Listener works");
    }

}
