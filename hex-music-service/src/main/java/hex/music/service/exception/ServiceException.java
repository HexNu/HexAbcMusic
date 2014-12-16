package hex.music.service.exception;

/**
 * Created 2014-12-10
 *
 * @author hl
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
