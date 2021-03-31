package by.learning.web.model.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Base dao interface with default methods
 *
 * @author Illia Aheyeu
 */
public interface BaseDao {


    /**
     * Close statement.
     *
     * @param statement database statement
     */
    default void close(Statement statement) {
        final Logger logger = LogManager.getLogger();
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Statement was`t closed", exception);
            }
        }
    }


    /**
     * Close database connection.
     *
     * @param connection database connection
     */
    default void close(Connection connection) {
        final Logger logger = LogManager.getLogger();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "Connection was`t closed", exception);
            }
        }
    }

    /**
     * Close statement resultSet.
     *
     * @param resultSet database resultSet
     */
    default void close(ResultSet resultSet) {
        final Logger logger = LogManager.getLogger();
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                logger.log(Level.ERROR, "ResultSet was`t close", exception);
            }
        }
    }

    /**
     * Rollback.
     *
     * @param connection database connection
     */
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

    /**
     * Set <code>true</code> to connection auto commit.
     *
     * @param connection database connection
     */
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
