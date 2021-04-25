package by.learning.web.model.dao.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.UserDao;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.testng.Assert.assertFalse;

public class UserDaoImplTest {

    UserDao userDao = new UserDaoImpl();

    @Test
    public void testAddResetToken() {
        String login = "isethesal";
        try {
            Optional<String> userEmail = userDao.findUserEmail(login);
            if (userEmail.isPresent()) {
                String resetToken = userDao.findResetToken(userEmail.get());
                if (resetToken.isBlank()) {
                    String token = userDao.createResetToken(userEmail.get()).orElseThrow();
                    assertFalse(token.isBlank());
                }
            } else {
                Assert.fail();
            }
        } catch (DaoException | NoSuchElementException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testClearResetTokens() {
        try {
            userDao.clearResetTokens();
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }
}