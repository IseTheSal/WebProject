package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.impl.UserDaoImpl;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.util.CryptEncoder;
import by.learning.web.validator.UserValidator;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<String> registerUser(String firstname, String lastname, String login,
                                    String password, String repeatPassword, String email, User.Role role) throws ServiceException {
        Set<ValidationInformation> validationInformation = UserValidator.isUserValid(firstname, lastname, login, password, repeatPassword, email);
        Set<String> valueValidInfo = validationInformation.stream().map(ValidationInformation::getInfoValue).collect(Collectors.toSet());
        if (!valueValidInfo.isEmpty()) {
            return valueValidInfo;
        }
        logger.log(Level.DEBUG, "All user fields are valid");
        User user = new User(login, firstname, lastname, email, role);
        try {
            String cryptPassword = CryptEncoder.generateCrypt(password);
            boolean isRegistered = userDao.addUser(user, cryptPassword);
            valueValidInfo.add(isRegistered ? ValidationInformation.SUCCESS.getInfoValue() : ValidationInformation.FAIL.getInfoValue());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return valueValidInfo;
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

    @Override
    public Set<User> findAllUsers() throws ServiceException {
        Set<User> result;
        try {
            result = userDao.findAllUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
