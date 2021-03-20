package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao extends BaseDao {

    Optional<User> findUser(String login, String password) throws DaoException;

    boolean addUser(User user, String cryptPassword) throws DaoException;

    boolean changeUserEmail(int userId, String email) throws DaoException;

    Optional<String> findUserPassword(int userId) throws DaoException;

    boolean changeUserPassword(int userId, String newPassword) throws DaoException;

    Set<User> findAllClients() throws DaoException;

    Set<User> findAllUsers() throws DaoException;

    String createResetToken(String email) throws DaoException;

    int findUserIdByResetToken(String token) throws DaoException;

    void clearResetTokens() throws DaoException;

    String findResetToken(String userAttribute) throws DaoException;

    boolean removeResetToken(String token) throws DaoException;

    boolean isTokenExist(String resetToken) throws DaoException;

    Optional<String> findUserEmail(String login) throws DaoException;
}
