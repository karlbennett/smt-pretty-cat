package shiver.me.timbers.exceptions;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * This utility class contain methods to help with inspecting the Java standard error stream.
 */
public final class StandardErrUtils {

    private StandardErrUtils() {
    }

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static PrintStream replaceStandardError(OutputStream stream) {

        final PrintStream oldErr = System.err;

        System.setErr(new PrintStream(stream));

        return oldErr;
    }

    public static void restoreStandardError(PrintStream oldErr) {

        System.setErr(oldErr);
    }
}
