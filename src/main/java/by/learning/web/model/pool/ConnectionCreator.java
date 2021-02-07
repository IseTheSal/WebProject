package by.learning.web.model.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


class ConnectionCreator {

    private static final Logger logger = LogManager.getLogger(ConnectionCreator.class);
    private static final Properties PROPERTY = new Properties();
    //FIXME
    private static final String PROPERTY_PATH = "C:\\Users\\illya\\Desktop\\Epam\\Epam Learning\\Servlet\\src\\main\\resources\\database.properties";
    private static final String DRIVER_KEY = "db.driver";
    private static final String URL = "db.url";

    static {
        try {
            FileReader fileReader = new FileReader(PROPERTY_PATH);
            PROPERTY.load(fileReader);
            String property = PROPERTY.getProperty(DRIVER_KEY);
            Class.forName(property);
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.FATAL, e);
        }
    }

    private ConnectionCreator() {
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(PROPERTY.getProperty(URL), PROPERTY);
    }
}
