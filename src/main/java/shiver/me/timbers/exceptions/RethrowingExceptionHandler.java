package shiver.me.timbers.exceptions;

/**
 * This exception handler will simply rethrow any exception it receives.
 */
public class RethrowingExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {

    @Override
    public void handle(T throwable) throws T {

        throw throwable;
    }
}
