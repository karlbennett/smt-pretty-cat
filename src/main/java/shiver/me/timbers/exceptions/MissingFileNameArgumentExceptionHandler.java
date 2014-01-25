package shiver.me.timbers.exceptions;

import static shiver.me.timbers.exceptions.ErrorMessages.USAGE;

public class MissingFileNameArgumentExceptionHandler extends AbstractExceptionHandler<ArrayIndexOutOfBoundsException> {

    public static final String MISSING_FILE_NAME_ERROR = "The path to a file must be supplied.";

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
