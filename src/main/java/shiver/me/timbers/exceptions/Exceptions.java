package shiver.me.timbers.exceptions;

import shiver.me.timbers.transform.Container;

import java.util.concurrent.Callable;

import static shiver.me.timbers.checks.Checks.isNotNull;

/**
 * This utility class contains the method and interface that is used to handle any exceptions thrown during the
 * execution of {@link shiver.me.timbers.PrettyCat#main(String[])}.
 */
public class Exceptions {

    private Exceptions() {
    }

    public static final int SUCCESS = 0;

    /**
     * Any exception thrown from with the callable passed to this method with have it's exceptions handled by the
     * supplied handlers.
     *
     * @param handlers all handler implementations mapped to the {@code Class} of their respective exceptions.
     * @param callable this callable should be implemented with the logic that requires exception handling.
     * @return {@link #SUCCESS} if no exception is handled otherwise the error code returned by the exception handler.
     */
    public static int withExceptionHandling(Container<Class, ExceptionHandler> handlers, Callable<Void> callable)
            throws Throwable {

        try {

            callable.call();

            return SUCCESS;

        } catch (RuntimeException e) {

            return isNotNull(e.getCause()) ? handle(handlers, e.getCause()) : handle(handlers, e);

        } catch (Throwable e) {

            return handle(handlers, e);
        }
    }

    @SuppressWarnings("unchecked")
    private static int handle(Container<Class, ExceptionHandler> handlers, Throwable t) throws Throwable {

        final ExceptionHandler handler = handlers.get(t.getClass());
        return handler.handle(t);
    }
}
