package shiver.me.timbers.exceptions;

/**
 * This exception handler provides a constructor for setting the exception type.
 */
public abstract class AbstractExceptionHandler<T extends Throwable> implements ExceptionHandler<T> {

    private final Class<T> exceptionType;

    protected AbstractExceptionHandler(Class<T> exceptionType) {

        this.exceptionType = exceptionType;
    }

    @Override
    public Class<T> getExceptionType() {

        return exceptionType;
    }
}
