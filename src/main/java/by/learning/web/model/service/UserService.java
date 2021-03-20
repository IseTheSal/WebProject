package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> singIn(String login, String password) throws ServiceException;

    Set<String> registerUser(String name, String lastname, String login,
                             String password, String repeatPassword, String email, User.Role role) throws ServiceException;

    boolean changeEmail(int userId, String email, String repeatEmail) throws ServiceException;

    boolean changeUserPassword(int userId, String oldPassword, String newPassword, String newPasswordRepeat) throws ServiceException;

    Set<String> resetPassword(String resetToken, String newPassword, String newPasswordRepeat) throws ServiceException;

    Set<String> sentResetPasswordLink(String reestablishParameter) throws ServiceException;

    Set<User> findAllUsers() throws ServiceException;

    Optional<String> findUserEmailByLogin(String login) throws ServiceException;

    boolean isTokenExist(String resetToken) throws ServiceException;
}
