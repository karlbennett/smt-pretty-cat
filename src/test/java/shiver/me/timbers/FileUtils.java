package shiver.me.timbers;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class contains methods to help access test data files.
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static File testFile() {

        final URL url = FileUtils.class.getResource("Test.txt");

        return new File(url.getFile());
    }

    public static InputStream testInputStream() {

        return FileUtils.class.getResourceAsStream("Test.txt");
    }

    public static String testText() {

        try {

            return IOUtils.toString(testInputStream());

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
