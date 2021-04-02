package by.learning.web.exception;

/**
 * <pre>An exception that provides information on a database access error or other errors.</pre>
 *
 * @author Illia Aheyeu
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
