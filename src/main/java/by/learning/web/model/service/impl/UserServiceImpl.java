package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.impl.UserDaoImpl;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.util.CryptEncoder;
import by.learning.web.validator.UserValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    public UserServiceImpl() {
    }

    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    public Optional<User> singIn(String login, String password) throws ServiceException {
        Optional<User> result = Optional.empty();
        if (UserValidator.isLoginValid(login)
                && UserValidator.isPasswordValid(password)) {
            try {
                result = userDao.findUser(login, password);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public boolean registerUser(String firstname, String lastname, String login,
                                String password, String repeatPassword, String email) throws ServiceException {
        boolean isValid = true;
        if (!repeatPassword.equals(password)) {
            logger.log(Level.WARN, "Passwords not equal");
            isValid = false;
        }

        if (!(UserValidator.isLoginValid(login) && UserValidator.isPasswordValid(password)
                && UserValidator.isNameValid(firstname) && UserValidator.isNameValid(lastname)
                && UserValidator.isEmailValid(email))) {
            logger.log(Level.WARN, "Not valid");
            isValid = false;
        }

        boolean isRegister = false;
        if (isValid) {
            logger.log(Level.DEBUG, "All user fields are valid");
            User user = new User(login, firstname, lastname, email);
            try {
                String cryptPassword = CryptEncoder.generateCrypt(password);
                isRegister = userDao.addUser(user, cryptPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isRegister;
    }
}
