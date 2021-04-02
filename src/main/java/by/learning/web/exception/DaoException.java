package by.learning.web.exception;

/**
 * <pre>An exception that provides information on a database access error or other errors.</pre>
 *
 * @author Illia Aheyeu
 */
public class DaoException extends Exception {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
