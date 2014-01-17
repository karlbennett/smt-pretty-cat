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
     * Anything exception thrown from with the callable passed to this method with have it's exceptions handled by the
     * supplied handlers.
     *
     * @param handlers all handler implementations mapped to the {@code Class} of their respective exceptions.
     * @param callable this callable should be implemented with the logic that requires exception handling.
     */
    @SuppressWarnings("unchecked")
    public static void withExceptionHandling(Container<Class, ExceptionHandler> handlers, Callable<Void> callable) {

        try {

            callable.call();

        } catch (Throwable e) {

            final ExceptionHandler handler = handlers.get(e.getClass());
            handler.handle(e);
        }
    }

    public static interface ExceptionHandler<T extends Throwable> {

        public void handle(T throwable);
    }
}
