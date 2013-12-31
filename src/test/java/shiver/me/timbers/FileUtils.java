package shiver.me.timbers;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class contains methods to help access test data files.
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static InputStream testInputStream() {

        return NullTransformerTest.class.getResourceAsStream("Test.txt");
    }

    public static String testText() {

        try {

            return IOUtils.toString(testInputStream());

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
