package shiver.me.timbers.exceptions;

/**
 * This exception handler will log the error message of the handled throwables to the error stream of the supplied log.
 */
public class MissingFileNameArgumentExceptionHandler extends AbstractExceptionHandler<ArrayIndexOutOfBoundsException> {

    public static final String MISSING_FILE_NAME_ERROR = "The path to a file must be supplied.";
    public static final String USAGE = "Usage: pcat [FILE_PATH]";

    public static final int MISSING_FILE_ERROR_CODE = 2;

    public MissingFileNameArgumentExceptionHandler() {
        super(ArrayIndexOutOfBoundsException.class);
    }

    @Override
    public int handle(ArrayIndexOutOfBoundsException throwable) {

        System.err.println(MISSING_FILE_NAME_ERROR);
        System.err.println(USAGE);

        return MISSING_FILE_ERROR_CODE;
    }
}
