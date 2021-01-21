package by.learning.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class CryptEncoder {

    private static final Logger logger = LogManager.getLogger(CryptEncoder.class);

    private static final int LOG_ROUND = 10;

    public static String generateCrypt(String password) {
        String salt = BCrypt.gensalt(LOG_ROUND);
        String hashed = BCrypt.hashpw(password, salt);
        return hashed;
    }

    public static boolean check(String password, String cryptValue) {
        boolean result = BCrypt.checkpw(password, cryptValue);
        logger.log(Level.DEBUG, "Bcrypt match: " + result);
        return result;
    }
}
