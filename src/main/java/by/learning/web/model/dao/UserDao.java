package by.learning.web.model.dao;

import by.learning.web.exception.DaoException;
import by.learning.web.model.entity.User;

import java.util.Optional;
import java.util.Set;

/**
 * <pre>User dao interface.</pre>
 *
 * @author Illia Aheyeu
 */
public interface UserDao extends BaseDao {

    /**
     * Search {@link User user}.
     *
     * @param login    {@link User user} login value
     * @param password {@link User user} login value
     * @return Optional {@link User user}
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Optional<User> findUser(String login, String password) throws DaoException;

    /**
     * Add new {@link User user}.
     *
     * @param user          {@link User user}
     * @param cryptPassword encrypted {@link User user} password
     * @return <code>true</code> if user successfully was created, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     * @see by.learning.web.util.CryptEncoder
     */
    boolean addUser(User user, String cryptPassword) throws DaoException;

    /**
     * Change existing {@link User user} email.
     *
     * @param userId {@link User user} id
     * @param email  new {@link User user} email
     * @return <code>true</code> if email successfully was changed, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean changeUserEmail(int userId, String email) throws DaoException;

    /**
     * Search encrypted {@link User user} password.
     *
     * @param userId {@link User user} id
     * @return Optional encrypted password
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     * @see by.learning.web.util.CryptEncoder
     */
    Optional<String> findUserPassword(int userId) throws DaoException;

    /**
     * Change password of existing {@link User user}.
     *
     * @param userId      {@link User user} id
     * @param newPassword encrypted {@link User user} password
     * @return <code>true</code> if password successfully was changed, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     * @see by.learning.web.util.CryptEncoder
     */
    boolean changeUserPassword(int userId, String newPassword) throws DaoException;

    /**
     * Search all users with role Client.
     *
     * @return <code>Set</code> of clients
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     * @see User.Role
     */
    Set<User> findAllClients() throws DaoException;

    /**
     * Search all users.
     *
     * @return <code>Set</code> of users
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Set<User> findAllUsers() throws DaoException;

    /**
     * Create token to reset user`s password.
     *
     * @param email {@link User user} email
     * @return Reset token value
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    String createResetToken(String email) throws DaoException;

    /**
     * Search user id by existing token.
     *
     * @param token reset password token
     * @return user id
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    int findUserIdByResetToken(String token) throws DaoException;

    /**
     * Delete all time-expired tokens.
     *
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    void clearResetTokens() throws DaoException;

    /**
     * Search reset password token.
     *
     * @param userAttribute email or login of {@link User user}.
     * @return reset password token
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    String findResetToken(String userAttribute) throws DaoException;

    /**
     * Remove used reset password token.
     *
     * @param token reset password token
     * @return <code>true</code> if token successfully was removed, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean removeResetToken(String token) throws DaoException;

    /**
     * Check if token exist.
     *
     * @param resetToken reset token value
     * @return <code>true</code> if token exists, otherwise <code>false</code>
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    boolean isTokenExist(String resetToken) throws DaoException;

    /**
     * Search {@link User user} email.
     *
     * @param login {@link User user`s} login
     * @return Optional {@link User user`s} email
     * @throws DaoException if {@link java.sql.SQLException} was thrown
     */
    Optional<String> findUserEmail(String login) throws DaoException;
}
