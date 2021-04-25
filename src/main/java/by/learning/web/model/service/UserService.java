package by.learning.web.model.service;

import by.learning.web.exception.ServiceException;
import by.learning.web.model.entity.User;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * <pre>User service interface.</pre>
 *
 * @author Illia Aheyeu
 * @see User
 */
public interface UserService {
    /**
     * Allows sign in by login and password.
     *
     * @param login    User`s login value
     * @param password User`s password value
     * @return Optional {@link User}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Optional<User> singIn(String login, String password) throws ServiceException;

    /**
     * Create new {@link User}.
     *
     * @param name           User`s name value
     * @param lastname       User`s last name value
     * @param login          User`s login value
     * @param password       User`s password value
     * @param repeatPassword repeated password value
     * @param email          User`s email value
     * @param role           {@link User.Role} value
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if user was registered return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with {@link by.learning.web.validator.ValidationInformation FAIL} and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     * @see User.Role
     */
    Set<String> registerUser(String name, String lastname, String login,
                             String password, String repeatPassword, String email, User.Role role) throws ServiceException;

    /**
     * Change email of existing user.
     *
     * @param userId      id of certain user
     * @param email       new email value
     * @param repeatEmail new repeated email value
     * @return <code>true</code> if email was changed, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean changeEmail(int userId, String email, String repeatEmail) throws ServiceException;

    /**
     * Change User`s password
     *
     * @param userId            id of certain user
     * @param oldPassword       old password value
     * @param newPassword       new password value
     * @param newPasswordRepeat new repeated password value
     * @return <code>true</code> if password was changed, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean changeUserPassword(int userId, String oldPassword, String newPassword, String newPasswordRepeat) throws ServiceException;

    /**
     * Create reset password token, used when user forgot his password.
     *
     * @param resetToken        reset password token value
     * @param newPassword       new password value
     * @param newPasswordRepeat new repeated password value
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if password was reset return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with {@link by.learning.web.validator.ValidationInformation FAIL} and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> resetPassword(String resetToken, String newPassword, String newPasswordRepeat) throws ServiceException;

    /**
     * Sent reset password link to User`s email.
     *
     * @param reestablishParameter {@link User User`s} login or email
     * @return <code>Set</code> of {@link by.learning.web.validator.ValidationInformation validation info}, if email was send return <code>Set</code> with {@link by.learning.web.validator.ValidationInformation SUCCESS}, otherwise with {@link by.learning.web.validator.ValidationInformation FAIL} and another issues information
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<String> sendResetPasswordLink(String reestablishParameter) throws ServiceException;

    /**
     * Search all users.
     *
     * @return <code>Set</code> of {@link User users}
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Set<User> findAllUsers() throws ServiceException;

    /**
     * Search {@link User} email.
     *
     * @param login {@link User User`s} login value
     * @return Optional <code>String</code> with user`s email
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    Optional<String> findUserEmailByLogin(String login) throws ServiceException;

    /**
     * Check if reset password token exists.
     *
     * @param resetToken reset password token value
     * @return <code>true</code> if token exists, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean isTokenExist(String resetToken) throws ServiceException;

    /**
     * Increase user balance.
     *
     * @param userId {@link User user} id
     * @param money  increased value
     * @return <code>true</code> if balance was increased, otherwise <code>false</code>
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    boolean increaseUserBalance(int userId, String money) throws ServiceException;

    /**
     * Find user balance.
     *
     * @param userId {@link User user} id
     * @return User money balance
     * @throws ServiceException if {@link by.learning.web.exception.DaoException DaoException} was thrown
     */
    BigDecimal findUserBalance(int userId) throws ServiceException;
}
