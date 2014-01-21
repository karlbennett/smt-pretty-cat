package shiver.me.timbers.exceptions;

/**
 * Implement this interface to handle specific exceptions.
 */
public interface ExceptionHandler<T extends Throwable> {

    /**
     * @return the type of exception that is being handled.
     */
    public Class<T> getExceptionType();

    /**
     * @param throwable the exception that was thrown.
     */
    public void handle(T throwable) throws T;
}