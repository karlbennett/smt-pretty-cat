package shiver.me.timbers.exceptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.exceptions.StandardErrUtils.replaceStandardError;
import static shiver.me.timbers.exceptions.StandardErrUtils.restoreStandardError;

public class ErrorLoggingExceptionHandlerTest {

    private PrintStream oldErr;
    private ByteArrayOutputStream err;

    @Before
    public void setup() {

        oldErr = replaceStandardError(err = new ByteArrayOutputStream());
    }

    @After
    public void tearDown() {

        restoreStandardError(oldErr);
    }

    @Test
    public void testHandle() {

        final String TEST_MESSAGE = "test exception message.";

        new ErrorLoggingExceptionHandler<Exception>(Exception.class).handle(new Exception(TEST_MESSAGE));

        assertEquals("the error message should be sent to standard error.", TEST_MESSAGE + '\n', err.toString());
    }

    @Test
    public void testHandleWithNullMessage() {

        new ErrorLoggingExceptionHandler<Exception>(Exception.class).handle(new Exception());

        assertEquals("the error message should be sent to standard error.", null + "\n", err.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testHandleWithNullException() {

        new ErrorLoggingExceptionHandler<Exception>(Exception.class).handle(null);
    }
}
