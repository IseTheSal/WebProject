package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> singIn(String login, String password);

    boolean registerUser(String name, String lastname, String login,
                         String password, String repeatPassword, String email);

    int findUserIndex(User user) throws ServiceException;

}
