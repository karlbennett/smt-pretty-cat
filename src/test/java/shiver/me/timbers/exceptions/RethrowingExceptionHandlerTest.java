package shiver.me.timbers.exceptions;

import org.junit.Test;

public class RethrowingExceptionHandlerTest {

    @Test(expected = Exception.class)
    public void testHandleWithException() throws Exception {

        new RethrowingExceptionHandler<Exception>().handle(new Exception());
    }

    @Test(expected = Error.class)
    public void testHandleWithError() throws Exception {

        new RethrowingExceptionHandler<Error>().handle(new Error());
    }

    @Test(expected = NullPointerException.class)
    public void testHandleWithNull() throws Throwable {

        new RethrowingExceptionHandler<Throwable>().handle(null);
    }
}
