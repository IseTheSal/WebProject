package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.Optional;

public interface UserDao extends CloseableDao {

    Optional<User> findUser(String login, String password) throws DaoException;

    boolean exist(String value, String sqlRequest) throws DaoException;

    boolean addUser(User user, String cryptPassword) throws DaoException;
}
