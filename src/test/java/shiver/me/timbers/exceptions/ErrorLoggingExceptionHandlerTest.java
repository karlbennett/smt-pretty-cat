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

    private static final int ERROR_CODE_ONE = 1;
    private static final int ERROR_CODE_TWO = 2;
    private static final int ERROR_CODE_THREE = 3;

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

        assertEquals("the correct error code should be returned.", ERROR_CODE_ONE,
                new ErrorLoggingExceptionHandler<Exception>(Exception.class, ERROR_CODE_ONE)
                        .handle(new Exception(TEST_MESSAGE)));

        assertEquals("the error message should be sent to standard error.", TEST_MESSAGE + '\n', err.toString());
    }

    @Test
    public void testHandleWithNullMessage() {

        assertEquals("the correct error code should be returned.", ERROR_CODE_TWO,
                new ErrorLoggingExceptionHandler<Exception>(Exception.class, ERROR_CODE_TWO).handle(new Exception()));

        assertEquals("the error message should be sent to standard error.", null + "\n", err.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testHandleWithNullException() {

        new ErrorLoggingExceptionHandler<Exception>(Exception.class, ERROR_CODE_THREE).handle(null);
    }
}
