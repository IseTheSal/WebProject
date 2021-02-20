package by.learning.web.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface BaseDao {

    default void close(Statement statement) {
        final Logger logger = LogManager.getLogger();
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Statement was`t closed");
            }
        }
    }

    default void close(Connection connection) {
        final Logger logger = LogManager.getLogger();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Connection was`t closed");
            }
        }
    }

    default void close(ResultSet resultSet) {
        final Logger logger = LogManager.getLogger();
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "ResultSet was`t close");
            }
        }
    }

    default void rollback(Connection connection) {
        final Logger logger = LogManager.getLogger();
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
            }
        }
    }

    default void setAutoCommitTrue(Connection connection) {
        final Logger logger = LogManager.getLogger();
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
            }
        }
    }
}
