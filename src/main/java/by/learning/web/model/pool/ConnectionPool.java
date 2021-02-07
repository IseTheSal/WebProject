package by.learning.web.model.pool;


import by.learning.web.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {
    INSTANCE;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int POOL_SIZE = 10;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final Queue<ProxyConnection> engagedConnections;

    ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>();
        engagedConnections = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionCreator.createConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
            } catch (SQLException exception) {
                logger.log(Level.FATAL, exception);
            }
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            engagedConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, e);
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (ProxyConnection.class == connection.getClass()) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            if (engagedConnections.remove(proxyConnection)) {
                freeConnections.offer(proxyConnection);
                logger.log(Level.DEBUG, "Connection was released");
            } else {
                logger.log(Level.WARN, "Connection was not released");
            }
        } else {
            logger.log(Level.WARN, "Incorrect connection type");
        }
    }

    public void destroyPool() throws ConnectionPoolException {
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
    }

    private void deregisterDrivers() throws ConnectionPoolException {
        try {
            while (DriverManager.getDrivers().hasMoreElements()) {
                Driver driver = DriverManager.getDrivers().nextElement();
                DriverManager.deregisterDriver(driver);
            }
            logger.log(Level.INFO, "Driver was deregistered");
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }
}
