package by.learning.web.util;

import org.testng.annotations.Test;

public class MailSenderTest {

    MailSender mailSender = MailSender.getInstance();

    @Test
    public void testSendMessage() {
        String to = "isethesal@gmail.com";
        String body = "Test complete";
        String title = "Game";
        mailSender.sendMessage(to, title, body);
    }
}