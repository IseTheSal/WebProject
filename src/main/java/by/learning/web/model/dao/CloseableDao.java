package by.learning.web.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface CloseableDao {

    default void close(Statement statement) {
        final Logger logger = LogManager.getLogger(CloseableDao.class);
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                logger.log(Level.DEBUG, "Statement was`t closed");
            }
        }
    }

    default void close(Connection connection) {
        final Logger logger = LogManager.getLogger(CloseableDao.class);
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.DEBUG, "Connection was`t closed");
            }
        }
    }
}
