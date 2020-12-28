package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> findUser(String login, String password);

    void add(User user) throws DaoException;

    boolean removeById(int id);

    boolean isLoginExist(String login);

    boolean isEmailExist(String email);
}
