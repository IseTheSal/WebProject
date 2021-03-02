package by.learning.web.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


class ConnectionCreator {

    private static final Logger logger = LogManager.getLogger();
    private static final Properties PROPERTY = new Properties();
    private static final String PROPERTY_PATH = "/property/database.properties";
    private static final String DRIVER_KEY = "db.driver";
    private static final String URL = "db.url";

    static {
        try {
            PROPERTY.load(ConnectionCreator.class.getResourceAsStream(PROPERTY_PATH));
            String driverName = PROPERTY.getProperty(DRIVER_KEY);
            Class.forName(driverName);
        } catch (Exception e) {
            logger.log(Level.FATAL, e);
            throw new RuntimeException(e);
        }
    }

    private ConnectionCreator() {
    }

    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTY.getProperty(URL), PROPERTY);
    }
}
