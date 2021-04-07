package by.learning.web.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class UserValidatorTest {

    @Test
    public void testEmailValidOne() {
        System.out.println(LocalTime.now());
        String email = "isethesal@gmail.com";
        Assert.assertTrue(UserValidator.isEmailValid(email));
    }

    @Test
    public void testEmailValidTwo() {
        System.out.println(LocalTime.now());
        String email = "isethesal@gmаil,com";
        Assert.assertFalse(UserValidator.isLoginValid(email));
    }

    @Test
    public void testEmailValidThree() {
        System.out.println(LocalTime.now());
        String email = "isethesal@gmail.cam";
        Assert.assertFalse(UserValidator.isLoginValid(email));
    }

    @Test
    public void testLoginValidOne() {
        System.out.println(LocalTime.now());
        String login = "sdfsh_123_$%";
        Assert.assertFalse(UserValidator.isLoginValid(login));
    }

    @Test
    public void testLoginValidTwo() {
        System.out.println(LocalTime.now());
        String login = "!@#$%^&*(";
        Assert.assertFalse(UserValidator.isLoginValid(login));
    }

    @Test
    public void testLoginValidThree() {
        System.out.println(LocalTime.now());
        String login = "*sdufh*hdsf*";
        Assert.assertFalse(UserValidator.isLoginValid(login));
    }
}