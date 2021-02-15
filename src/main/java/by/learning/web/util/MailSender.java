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
    private static final String SUBJECT_NAME = "Order from GameSpot";
    private static final String MESSAGE_FOOTER = "If you have any questions, write to our mail ";
    //fixme
    private static final String PROPERTIES_PATH = "C:\\Users\\illya\\Desktop\\Epam\\Epam Learning\\Servlet\\src\\main\\resources\\config\\mail.properties";

    private final Properties properties;

    public MailSender() {
        properties = new Properties();
        try {
            properties.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

    public void sendMessage(String recipient, String body) {
        Session session = init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(HOST_MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(SUBJECT_NAME);
            String messageBody = new StringBuilder(body).append("\n").append(MESSAGE_FOOTER).append(HOST_MAIL).toString();
            message.setText(messageBody);
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
