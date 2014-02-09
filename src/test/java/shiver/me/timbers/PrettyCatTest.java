package shiver.me.timbers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.BACKGROUND_COLOUR.BLACK;
import static shiver.me.timbers.ESCAPE.RESET;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;
import static shiver.me.timbers.FileUtils.TEST_JAVASCRIPT_FILE;
import static shiver.me.timbers.FileUtils.TEST_JAVA_FILE;
import static shiver.me.timbers.FileUtils.TEST_JSON_FILE;
import static shiver.me.timbers.FileUtils.TEST_PRETTY_JAVASCRIPT_FILE;
import static shiver.me.timbers.FileUtils.TEST_PRETTY_JAVA_FILE;
import static shiver.me.timbers.FileUtils.TEST_PRETTY_JSON_FILE;
import static shiver.me.timbers.FileUtils.TEST_PRETTY_XML_FILE;
import static shiver.me.timbers.FileUtils.TEST_TXT_FILE;
import static shiver.me.timbers.FileUtils.TEST_XML_FILE;
import static shiver.me.timbers.FileUtils.testFileContents;
import static shiver.me.timbers.FileUtils.testFilePath;
import static shiver.me.timbers.FileUtils.testTxtContents;

public class PrettyCatTest {

    private PrintStream originalStandardOut;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {

        out = replaceStandardOut();
    }

    @After
    public void tearDown() {

        restoreStandardOut();
    }

    @Test
    public void testCreate() {

        new PrettyCat();
    }

    @Test
    public void testRunWithUnknownFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_TXT_FILE)});

        assertEquals("an unknown file should be prettified.", prettyWrap(testTxtContents()), out.toString());
    }

    @Test
    public void testRunWithJavaFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_JAVA_FILE)});

        assertEquals("a Java file should be prettified.", testFileContents(TEST_PRETTY_JAVA_FILE), out.toString());
    }

    @Test
    public void testRunWithJavaScriptFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_JAVASCRIPT_FILE)});

        assertEquals("a Java file should be prettified.",
                testFileContents(TEST_PRETTY_JAVASCRIPT_FILE), out.toString());
    }

    @Test
    public void testRunWithXmlFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_XML_FILE)});

        assertEquals("a XML file should be prettified.", testFileContents(TEST_PRETTY_XML_FILE), out.toString());
    }

    @Test
    public void testRunWithJsonFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_JSON_FILE)});

        assertEquals("a JSON file should be prettified.", testFileContents(TEST_PRETTY_JSON_FILE), out.toString());
    }

    @Test
    public void testRunWithMultipleFiles() throws Throwable {

        PrettyCat.run(new String[]{
                testFilePath(TEST_JAVA_FILE),
                testFilePath(TEST_JSON_FILE),
                testFilePath(TEST_XML_FILE)
        });

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        final PrintStream expectedOut = new PrintStream(outputStream);
        expectedOut.print(testFileContents(TEST_PRETTY_JAVA_FILE));
        expectedOut.print(testFileContents(TEST_PRETTY_JSON_FILE));
        expectedOut.print(testFileContents(TEST_PRETTY_XML_FILE));

        assertEquals("a JSON file should be prettified.", outputStream.toString(), out.toString());
    }

    private ByteArrayOutputStream replaceStandardOut() {

        originalStandardOut = System.out;

        final ByteArrayOutputStream stream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(stream));

        return stream;
    }

    private void restoreStandardOut() {

        System.setOut(originalStandardOut);
    }

    private static String prettyWrap(String string) {

        return BLACK.escapeSequence() + BRIGHT_WHITE.escapeSequence() + string + RESET + '\n';
    }
}
