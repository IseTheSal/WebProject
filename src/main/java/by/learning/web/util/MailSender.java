package by.learning.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;


/**
 * <pre>Used to sent mails to User.</pre>
 *
 * @author Illia Aheyeu
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String HOST_MAIL = "gamespot.by@gmail.com";
    private static final String HOST_PASSWORD = "strongpassword";
    private static final String SUBJECT_NAME = "GameSpot!";
    private static final String MESSAGE_FOOTER = "If you have any questions, write to our email";
    private static final String PROPERTIES_PATH = "/property/mail.properties";
    private static final Properties PROPERTIES;
    private static volatile MailSender INSTANCE;

    private MailSender() {
    }

    static {
        PROPERTIES = new Properties();
        try {
            PROPERTIES.load(MailSender.class.getResourceAsStream(PROPERTIES_PATH));
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

    /**
     * Return object {@link MailSender} with double check volatile "Singleton" pattern
     *
     * @return {@link MailSender} INSTANCE
     */
    public static MailSender getInstance() {
        MailSender local = INSTANCE;
        if (local == null) {
            synchronized (MailSender.class) {
                local = INSTANCE;
                if (local == null) {
                    INSTANCE = local = new MailSender();
                }
            }
        }
        return local;
    }

    /**
     * Send message to User`s email
     *
     * @param recipient User`s email
     * @param gameTitle Game title value
     * @param body      Message body value
     */
    public void sendMessage(String recipient, String gameTitle, String body) {
        Session session = init();
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(HOST_MAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            String title = new StringBuilder(SUBJECT_NAME).append("\s").append(gameTitle).toString();
            message.setSubject(title);
            String messageBody = new StringBuilder(body).append("\n").append(MESSAGE_FOOTER).append("\s").append(HOST_MAIL).toString();
            message.setText(messageBody);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.log(Level.ERROR, e);
        }
    }

    private Session init() {
        Session session = Session.getInstance(PROPERTIES, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(HOST_MAIL, HOST_PASSWORD);
            }
        });
        session.setDebug(true);
        return session;
    }
}
