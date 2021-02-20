package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> singIn(String login, String password) throws ServiceException;

    boolean registerUser(String name, String lastname, String login,
                         String password, String repeatPassword, String email) throws ServiceException;

    boolean changeEmail(int userId, String email, String repeatEmail) throws ServiceException;

    boolean changeUserPassword(int userId, String oldPassword, String newPassword, String newPasswordRepeat) throws ServiceException;
}
