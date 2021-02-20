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
    private static final Logger logger = LogManager.getLogger();

    public UserServiceImpl() {
    }

    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    @Override
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

    @Override
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

    @Override
    public boolean changeEmail(int userId, String email, String repeatEmail) throws ServiceException {
        if (!email.equals(repeatEmail) || !UserValidator.isEmailValid(email)) {
            return false;
        }
        boolean isChanged;
        try {
            isChanged = userDao.changeUserEmail(userId, email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return isChanged;
    }

    @Override
    public boolean changeUserPassword(int userId, String oldPassword, String newPassword, String newPasswordRepeat) throws ServiceException {
        if (!newPassword.equals(newPasswordRepeat) || !UserValidator.isPasswordValid(newPassword)) {
            return false;
        }
        Optional<String> userPassword;
        boolean isValid = false;
        try {
            userPassword = userDao.findUserPassword(userId);
            if (userPassword.isPresent()) {
                String oldUserPassword = userPassword.get();
                isValid = CryptEncoder.check(oldPassword, oldUserPassword);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        boolean isChanged = false;
        if (isValid) {
            String cryptPassword = CryptEncoder.generateCrypt(newPassword);
            try {
                isChanged = userDao.changeUserPassword(userId, cryptPassword);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isChanged;
    }
}
