package shiver.me.timbers.exceptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.exceptions.FileNotFoundExceptionHandler.FILE_NOT_FOUND_ERROR_CODE;
import static shiver.me.timbers.exceptions.StandardErrUtils.NEW_LINE;
import static shiver.me.timbers.exceptions.StandardErrUtils.replaceStandardError;
import static shiver.me.timbers.exceptions.StandardErrUtils.restoreStandardError;

public class FileNotFoundExceptionHandlerTest {

    private static final String TEST_ERROR_MESSAGE = "test file not found error message.";

    private static final String ERROR_MESSAGE = TEST_ERROR_MESSAGE + NEW_LINE;

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
    public void testHandle() throws Exception {

        assertEquals("the correct error code should be returned.", FILE_NOT_FOUND_ERROR_CODE,
                new FileNotFoundExceptionHandler().handle(new FileNotFoundException(TEST_ERROR_MESSAGE)));

        assertEquals("the error message should be sent to standard error.", ERROR_MESSAGE, err.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testHandleWithNull() throws Exception {

        new FileNotFoundExceptionHandler().handle(null);
    }
}
