package by.learning.web.model.service.impl;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.impl.UserDaoImpl;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
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

    public Optional<User> singIn(String login, String password) {
        Optional<User> result = Optional.empty();
        if (UserValidator.isLoginValid(login)
                && UserValidator.isPasswordValid(password)) {
            result = userDao.findUser(login, password);
        }
        return result;
    }

    public boolean registerUser(String name, String lastname, String login,
                                String password, String repeatPassword, String email) {
        boolean isRegister = true;
        logger.log(Level.DEBUG, name + " " + lastname + " " + login + " " + password + " " + repeatPassword + " " + email);
        if (!repeatPassword.equals(password)) {
            logger.log(Level.WARN, "Passwords not equals");
            isRegister = false;
        }
        if (!(UserValidator.isLoginValid(login) && UserValidator.isPasswordValid(password)
                && UserValidator.isNameValid(name) && UserValidator.isNameValid(lastname)
                && UserValidator.isEmailValid(email))) {
            logger.log(Level.WARN, "Not valid");
            logger.log(Level.WARN, login);
            logger.log(Level.WARN, password);
            logger.log(Level.WARN, name);
            logger.log(Level.WARN, lastname);
            logger.log(Level.WARN, email);
            isRegister = false;
        }
        if (userDao.isLoginExist(login) && userDao.isEmailExist(email)) {
            logger.log(Level.WARN, "This login or email already exist");
            isRegister = false;
        }

        if (isRegister) {
            User user = new User(login, name, lastname, password, email);
            addUser(user);
            logger.log(Level.INFO, "User was added");
            isRegister = true;

        }

        return isRegister;
    }

    private void addUser(User user) {
        logger.log(Level.DEBUG, "adding user");
        userDao.add(user);
    }

    public int findUserIndex(User user) throws ServiceException {
        Optional<Integer> userIndex = userDao.findUserIndex(user);
        if (userIndex.isEmpty()) {
            throw new ServiceException("This user does not exist");
        }
        return userIndex.get();
    }

}
