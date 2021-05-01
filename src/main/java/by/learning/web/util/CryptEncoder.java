package by.learning.web.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

/**
 * <pre>Encryptor class used to generate and check crypt password with salt.</pre>
 *
 * @author Illia Aheyeu
 * @see BCrypt
 */
public class CryptEncoder {
    private static final Logger logger = LogManager.getLogger();
    /**
     * The log2 of the number of rounds of hashing to apply - the work factor therefore increases as 2**log_rounds.
     */
    private static final int LOG_ROUND = 10;
    /**
     * Generate encrypted password.
     *
     * @param password User`s password value
     * @return encrypted password.
     */
    public static String generateCrypt(String password) {
        String salt = BCrypt.gensalt(LOG_ROUND);
        String hashed = BCrypt.hashpw(password, salt);
        return hashed;
    }

    /**
     * Check if unencrypted and encrypted passwords match
     *
     * @param password   unencrypted password value
     * @param cryptValue encrypted password value
     * @return <code>true</code> if password maths encrypted value, otherwise <code>false</code>
     */
    public static boolean check(String password, String cryptValue) {
        boolean result = BCrypt.checkpw(password, cryptValue);
        logger.log(Level.DEBUG, "Bcrypt match: " + result);
        return result;
    }
}
