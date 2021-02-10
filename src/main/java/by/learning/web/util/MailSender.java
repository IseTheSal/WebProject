package by.learning.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String HOST_MAIL = "gamespot.by@gmail.com";
    private static final String HOST_PASSWORD = "strongpassword";
    private static final String PROPERTIES_PATH = "src/main/resources/config/mail.properties";

    private final Properties properties;

    public MailSender() {
        properties = System.getProperties();
        try {
            properties.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public void sendMessage(String recipient, String subject, String body) {
        Session session = init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(HOST_MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, e);
        }
    }

    private Session init() {
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HOST_MAIL, HOST_PASSWORD);
            }
        });
        session.setDebug(true);
        return session;
    }
}
