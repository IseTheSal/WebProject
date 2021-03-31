package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.UserDao;
import by.learning.web.model.dao.impl.DaoInstance;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.UserService;
import by.learning.web.util.CryptEncoder;
import by.learning.web.util.MailSender;
import by.learning.web.validator.UserValidator;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();

    private static final UserDao userDao = DaoInstance.INSTANCE.getUserDao();
    ;

    private static final String PASSWORD_RESET_TITLE = "RESET YOUR PASSWORD";
    private static final String MAIL_BODY_TITLE = "Your link to reset password";
    private static final String RESET_LINK = "http://localhost:8080/resetPassword.do?command=open_reset_password&resetToken=";
    private static final String WARNING = "If it is not you, do NOT click this link!";

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

    @Override
    public Optional<String> findUserEmailByLogin(String login) throws ServiceException {
        Optional<String> userEmail;
        try {
            userEmail = userDao.findUserEmail(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userEmail;
    }

    //        1. clear tokens
//        2. find email
//        3. find token
//        4. if token is null -> create
//        5. if token exist create link
//        6. sent email with link
    @Override
    public Set<String> sentResetPasswordLink(String reestablishParameter) throws ServiceException {
        Set<String> info = new HashSet<>();
        try {
            String email;
            if (UserValidator.isEmailValid(reestablishParameter)) {
                email = reestablishParameter;
            } else if (UserValidator.isLoginValid(reestablishParameter) && findUserEmailByLogin(reestablishParameter).isPresent()) {
                email = findUserEmailByLogin(reestablishParameter).get();
            } else {
                info.add(ValidationInformation.INPUT_INCORRECT.getInfoValue());
                info.add(ValidationInformation.FAIL.getInfoValue());
                return info;
            }
            userDao.clearResetTokens();
            String resetToken = userDao.findResetToken(email);
            if (resetToken.isBlank()) {
                resetToken = userDao.createResetToken(email);
            }
            MailSender mailSender = new MailSender();
            String body = MAIL_BODY_TITLE + "\n" + RESET_LINK + resetToken + "\n" + WARNING;
            mailSender.sendMessage(email, PASSWORD_RESET_TITLE, body);
            info.add(ValidationInformation.SUCCESS.getInfoValue());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return info;
    }

    @Override
    public boolean isTokenExist(String resetToken) throws ServiceException {
        boolean exist;
        try {
            userDao.clearResetTokens();
            exist = userDao.isTokenExist(resetToken);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return exist;
    }

    @Override
    public Set<String> resetPassword(String resetToken, String newPassword, String newPasswordRepeat) throws ServiceException {
        Set<String> info = new HashSet<>();
        if (!UserValidator.isPasswordValid(newPassword)) {
            info.add(ValidationInformation.PASSWORD_INCORRECT.getInfoValue());
        } else if (!newPassword.equals(newPasswordRepeat)) {
            info.add(ValidationInformation.PASSWORDS_NOT_EQUAL.getInfoValue());
        }
        if (!info.isEmpty()) {
            info.add(ValidationInformation.FAIL.getInfoValue());
            return info;
        }
        try {
            userDao.clearResetTokens();
            int userId = userDao.findUserIdByResetToken(resetToken);
            if (userId < 0) {
                info.add(ValidationInformation.FAIL.getInfoValue());
                info.add(ValidationInformation.USER_NOT_FOUND.getInfoValue());
            } else {
                String password = CryptEncoder.generateCrypt(newPassword);
                boolean isChanged = userDao.changeUserPassword(userId, password);
                if (isChanged) {
                    info.add(ValidationInformation.SUCCESS.getInfoValue());
                    userDao.removeResetToken(resetToken);
                } else {
                    info.add(ValidationInformation.FAIL.getInfoValue());
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return info;
    }
}
