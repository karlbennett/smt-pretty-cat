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
     * @return an error code for the handled exception that will be returned from the program with a call to
     *         {@link System#exit(int)}.
     */
    public int handle(T throwable) throws T;
}