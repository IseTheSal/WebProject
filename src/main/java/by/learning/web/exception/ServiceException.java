package by.learning.web.exception;

/**
 * <pre>An exception that provides information on service errors.</pre>
 *
 * @author Illia Aheyeu
 */
public class ServiceException extends Exception {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
