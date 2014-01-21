package shiver.me.timbers.exceptions;

/**
 * This exception handler will log the error message of the handled throwables to the error stream of the supplied log.
 */
public class ErrorLoggingExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

    private final int errorCode;

    protected ErrorLoggingExceptionHandler(Class<T> exceptionType, int errorCode) {
        super(exceptionType);

        this.errorCode = errorCode;
    }

    @Override
    public int handle(T throwable) {

        System.err.println(throwable.getMessage());

        return errorCode;
    }
}
