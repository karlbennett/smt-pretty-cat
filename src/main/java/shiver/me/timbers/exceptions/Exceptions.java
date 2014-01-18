package shiver.me.timbers.exceptions;

import shiver.me.timbers.transform.Container;

import java.util.concurrent.Callable;

/**
 * This utility class contains the method and interface that is used to handle any exceptions thrown during the
 * execution of {@link shiver.me.timbers.PrettyCat#main(String[])}.
 */
public class Exceptions {

    private Exceptions() {
    }

    /**
     * Any exception thrown from with the callable passed to this method with have it's exceptions handled by the
     * supplied handlers.
     *
     * @param handlers all handler implementations mapped to the {@code Class} of their respective exceptions.
     * @param callable this callable should be implemented with the logic that requires exception handling.
     * @return true if no exception is thrown, otherwise false.
     */
    @SuppressWarnings("unchecked")
    public static boolean withExceptionHandling(Container<Class, ExceptionHandler> handlers, Callable<Void> callable)
            throws Throwable {

        try {

            callable.call();

            return true;

        } catch (Throwable e) {

            final ExceptionHandler handler = handlers.get(e.getClass());
            handler.handle(e);

            return false;
        }
    }
}
