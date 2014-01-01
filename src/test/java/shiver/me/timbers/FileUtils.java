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

    public static final String TEST_TXT_FILE = "Test.txt";

    public static final String TEST_JAVA_FILE = "Test.java";
    public static final String TEST_PRETTY_JAVA_FILE = "Test.java.pretty";

    public static final String TEST_XML_FILE = "Test.xml";
    public static final String TEST_PRETTY_XML_FILE = "Test.xml.pretty";

    public static String testFilePath(String fileName) {

        return testFile(fileName).getPath();
    }

    public static File testTxtFile() {

        return testFile(TEST_TXT_FILE);
    }

    public static File testFile(String fileName) {

        final URL url = FileUtils.class.getResource(fileName);

        return new File(url.getFile());
    }

    public static String testTxtContents() {

        return testFileContents(TEST_TXT_FILE);
    }

    public static String testFileContents(String fileName) {

        try {

            return IOUtils.toString(testInputStream(fileName));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public static InputStream testTxtInputStream() {

        return testInputStream(TEST_TXT_FILE);
    }

    public static InputStream testInputStream(String fileName) {

        return FileUtils.class.getResourceAsStream(fileName);
    }
}
