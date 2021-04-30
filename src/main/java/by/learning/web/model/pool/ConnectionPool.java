package by.learning.web.model.pool;


import by.learning.web.controller.Controller;
import by.learning.web.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>Enum with "Singleton" pattern used to manage {@link ProxyConnection}.</pre>
 *
 * @author Illia Aheyeu
 * @see ProxyConnection
 * @see ConnectionPool
 */
public enum ConnectionPool {
    INSTANCE;

    private final Logger logger = LogManager.getLogger();
    private static final int POOL_SIZE = 10;
    private static final int ERROR_CONNECTION_CREATOR = 3;
    private final BlockingQueue<ProxyConnection> freeConnections;


    ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>();
        int errorCounter = 0;
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException exception) {
                logger.log(Level.ERROR, exception);
                errorCounter++;
            }
        }
        if (errorCounter >= ERROR_CONNECTION_CREATOR) {
            logger.log(Level.FATAL, "Count of errors during connection creation - " + errorCounter);
            throw new RuntimeException("Count of errors during connection creation - " + errorCounter);
        }
    }

    /**
     * Taking {@link ProxyConnection} from <code>BlockingQueue</code>.
     *
     * @return {@link Connection} object
     * @throws ConnectionPoolException if interrupted while waiting
     */
    public Connection takeConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    /**
     * Release {@link ProxyConnection} back to <code>BlockingQueue</code>
     *
     * @param connection Connection object, which should be {@link ProxyConnection}
     */
    public void releaseConnection(Connection connection) {
        if (ProxyConnection.class == connection.getClass()) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            freeConnections.offer(proxyConnection);
            logger.log(Level.DEBUG, "Connection was released");
        } else {
            logger.log(Level.WARN, "Incorrect connection type");
        }
    }

    /**
     * Destroy connection pool. Called during program termination.
     *
     * @param object {@link Object} object. Used to check accessibility for pool destroy.
     * @throws ConnectionPoolException if {@link InterruptedException} or {@link SQLException} were thrown
     */
    public void destroyPool(Object object) throws ConnectionPoolException {
        if (object.getClass() == Controller.class) {
            try {
                for (int i = 0; i < POOL_SIZE; i++) {
                    ProxyConnection proxyConnection = freeConnections.take();
                    proxyConnection.reallyClose();
                }
                logger.log(Level.INFO, "Connection pool has been destroyed");
            } catch (InterruptedException | SQLException e) {
                logger.log(Level.ERROR, e);
                throw new ConnectionPoolException(e);
            } finally {
                deregisterDrivers();
            }
        } else {
            logger.log(Level.WARN, "Method available only for Controller class");
        }
    }

    /**
     * Deregister drivers. Called during program termination.
     *
     * @throws ConnectionPoolException if {@link SQLException} was thrown
     */
    private void deregisterDrivers() throws ConnectionPoolException {
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                DriverManager.deregisterDriver(driver);
            }
            logger.log(Level.INFO, "Drivers were deregistered");
        } catch (SQLException e) {
            logger.log(Level.ERROR, e);
            throw new ConnectionPoolException(e);
        }
    }
}
