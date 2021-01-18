package by.learning.web.model.dao.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.UserDao;
import by.learning.web.model.entity.User;
import by.learning.web.util.CryptEncoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static UserDaoImpl INSTANCE;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
        }
        return INSTANCE;
    }

    private final static String URL_TABLES = "jdbc:postgresql://localhost:5432/postgres?useUnicode=true;characterEncoding=UTF-8";
    private final static String LOGIN_TABLES = "postgres";
    private final static String PASS_TABLES = "aili61329";

    
    private final static String FIND_USER = "SELECT user_id, password, firstname, lastname, email, users.role " +
            "FROM users WHERE users.login = ?";

    private final static String CONTAIN_LOGIN = "SELECT COUNT(*) FROM users WHERE users.login = ?";

    private final static String CONTAIN_EMAIL = "SELECT COUNT(*) FROM users WHERE users.email = ?";

    private final static String ADD_USER = "INSERT INTO users (login, password, firstname, lastname, email, role) " +
            " VALUES(?,?,?,?,?,?)";


    @Override
    public Optional<User> findUser(String login, String password) throws DaoException {
        Optional<User> result = Optional.empty();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL_TABLES, LOGIN_TABLES, PASS_TABLES);
            preparedStatement = connection.prepareStatement(FIND_USER);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String cryptPassword = resultSet.getString(2);
            boolean isMatch = CryptEncoder.check(password, cryptPassword);
            if (isMatch) {
                int id = Integer.parseInt(resultSet.getString(1));
                String firstname = resultSet.getString(3);
                String lastname = resultSet.getString(4);
                String email = resultSet.getString(5);
                String roleString = resultSet.getString(6);
                User.Role role = User.Role.valueOf(roleString);
                result = Optional.of(new User(id, login, firstname, lastname, email, role));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return result;
    }

    public boolean existLogin(String login) throws DaoException {
        boolean result = exist(login, CONTAIN_LOGIN);
        logger.log(Level.DEBUG, "Is login exist: " + result);
        return result;
    }

    public boolean existEmail(String email) throws DaoException {
        boolean result = exist(email, CONTAIN_EMAIL);
        logger.log(Level.DEBUG, "Is email exist: " + result);
        return result;
    }


    public boolean exist(String value, String sqlRequest) throws DaoException {
        boolean result;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL_TABLES, LOGIN_TABLES, PASS_TABLES);
            preparedStatement = connection.prepareStatement(sqlRequest);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            int counter = 0;
            if (resultSet.next()) {
                counter = Integer.parseInt(resultSet.getString(1));
            }
            result = (counter > 0);
        } catch (SQLException ex) {
            logger.log(Level.DEBUG, ex);
            throw new DaoException(ex);
        } finally {
            close(preparedStatement);
            close(connection);
        }
        return result;
    }

    public boolean addUser(User user, String cryptPassword) throws DaoException {
        boolean result = false;
        String login = user.getLogin();
        String email = user.getEmail();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (!existEmail(email) && !existLogin(login)) {
            logger.log(Level.DEBUG, "Login and email are available");
            try {
                connection = DriverManager.getConnection(URL_TABLES, LOGIN_TABLES, PASS_TABLES);
                preparedStatement = connection.prepareStatement(ADD_USER);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, cryptPassword);
                preparedStatement.setString(3, user.getFirstname());
                preparedStatement.setString(4, user.getLastname());
                preparedStatement.setString(5, email);
                String role = user.getRole().toString();
                preparedStatement.setString(6, role);
                preparedStatement.executeUpdate();
                result = true;
                logger.log(Level.DEBUG, "New user register successfully");
            } catch (SQLException ex) {
                throw new DaoException(ex);
            } finally {
                close(preparedStatement);
                close(connection);
            }
        } else {
            logger.log(Level.DEBUG, "Fail during registration. Login or email exist");
        }
        return result;
    }

}
