package shiver.me.timbers.exceptions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.exceptions.MissingFileNameArgumentExceptionHandler.MISSING_FILE_ERROR_CODE;
import static shiver.me.timbers.exceptions.MissingFileNameArgumentExceptionHandler.MISSING_FILE_NAME_ERROR;
import static shiver.me.timbers.exceptions.MissingFileNameArgumentExceptionHandler.USAGE;
import static shiver.me.timbers.exceptions.StandardErrUtils.NEW_LINE;
import static shiver.me.timbers.exceptions.StandardErrUtils.replaceStandardError;
import static shiver.me.timbers.exceptions.StandardErrUtils.restoreStandardError;

public class MissingFileNameArgumentExceptionHandlerTest {

    private static final String ERROR_MESSAGE = MISSING_FILE_NAME_ERROR + NEW_LINE + USAGE + NEW_LINE;

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

        assertEquals("the correct error code should be returned.", MISSING_FILE_ERROR_CODE,
                new MissingFileNameArgumentExceptionHandler().handle(new ArrayIndexOutOfBoundsException()));

        assertEquals("the error message should be sent to standard error.", ERROR_MESSAGE, err.toString());
    }

    @Test
    public void testHandleWithNull() throws Exception {

        assertEquals("the correct error code should be returned.", MISSING_FILE_ERROR_CODE,
                new MissingFileNameArgumentExceptionHandler().handle(null));

        assertEquals("the error message should be sent to standard error.", ERROR_MESSAGE, err.toString());
    }
}
