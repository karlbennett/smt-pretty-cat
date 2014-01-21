package shiver.me.timbers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.ESCAPE.RESET;
import static shiver.me.timbers.FileUtils.TEST_JAVA_FILE;
import static shiver.me.timbers.FileUtils.TEST_PRETTY_JAVA_FILE;
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
    public void testMainWithUnknownFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_TXT_FILE)});

        assertEquals("an unknown file should not be prettified.", appendPrettySuffix(testTxtContents()),
                out.toString());
    }

    @Test
    public void testMainWithJavaFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_JAVA_FILE)});

        assertEquals("a Java file should be prettified.", testFileContents(TEST_PRETTY_JAVA_FILE), out.toString());
    }

    @Test
    public void testMainWithXmlFile() throws Throwable {

        PrettyCat.run(new String[]{testFilePath(TEST_XML_FILE)});

        assertEquals("a Java file should be prettified.", testFileContents(TEST_PRETTY_XML_FILE), out.toString());
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

    private static String appendPrettySuffix(String string) {

        return string + RESET + '\n';
    }
}
