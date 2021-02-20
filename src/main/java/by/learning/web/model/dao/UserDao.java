package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.Optional;

public abstract class UserDao implements BaseDao {

    protected abstract Optional<User> findUser(String login, String password) throws DaoException;

    protected abstract boolean exist(String value, String sqlRequest) throws DaoException;

    protected abstract boolean addUser(User user, String cryptPassword) throws DaoException;

    protected abstract boolean changeUserEmail(int userId, String email) throws DaoException;

    protected abstract Optional<String> findUserPassword(int userId) throws DaoException;

    protected abstract boolean changeUserPassword(int userId, String newPassword) throws DaoException;
}
