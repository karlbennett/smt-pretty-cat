package shiver.me.timbers.exceptions;

/**
 * This exception handler will log the error message of the handled throwables to the error stream of the supplied log.
 */
public class ErrorLoggingExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {

    @Override
    public void handle(T throwable) {

        System.err.println(throwable.getMessage());
    }
}
