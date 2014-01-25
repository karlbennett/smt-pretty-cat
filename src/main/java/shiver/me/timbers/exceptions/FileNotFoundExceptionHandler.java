package shiver.me.timbers.exceptions;

import java.io.FileNotFoundException;

public class FileNotFoundExceptionHandler extends AbstractExceptionHandler<FileNotFoundException> {

    public static final int FILE_NOT_FOUND_ERROR_CODE = 3;

    public FileNotFoundExceptionHandler() {
        super(FileNotFoundException.class);
    }

    @Override
    public int handle(FileNotFoundException throwable) throws FileNotFoundException {

        System.err.println(throwable.getMessage());

        return FILE_NOT_FOUND_ERROR_CODE;
    }
}
