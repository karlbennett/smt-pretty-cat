package shiver.me.timbers.properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.TestUtils.TEST_PROPERTIES_FILE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_FIVE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_FOUR;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_ONE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_THREE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_TWO;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_FIVE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_FOUR;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_ONE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_THREE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_TWO;
import static shiver.me.timbers.TestUtils.clearTestProperties;

public class GlobalPropertyLoaderTest {

    private static final String INVALID_PROPERTY_NAME = "this";

    private static final String INVALID_PROPERTY_VALUE = "is not a properties file.";

    private InputStream stream;
    private InputStream invalidStream;

    @Before
    public void setUp() {

        clearTestProperties();

        stream = GlobalPropertyLoaderTest.class.getClassLoader().getResourceAsStream(TEST_PROPERTIES_FILE);
        invalidStream = GlobalPropertyLoaderTest.class.getClassLoader().getResourceAsStream("test.txt");
    }

    @After
    public void tearDown() {

        clearTestProperties();
    }

    @Test
    public void testCreate() {

        new GlobalPropertyLoader(mock(InputStream.class));
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullInputStream() {

        new GlobalPropertyLoader(null);
    }

    @Test
    public void testLoad() {

        new GlobalPropertyLoader(stream).load();

        assertEquals("property one should be loaded.", TEST_PROPERTY_VALUE_ONE,
                System.getProperty(TEST_PROPERTY_NAME_ONE));
        assertEquals("property two should be loaded.", TEST_PROPERTY_VALUE_TWO,
                System.getProperty(TEST_PROPERTY_NAME_TWO));
        assertEquals("property three should be loaded.", TEST_PROPERTY_VALUE_THREE,
                System.getProperty(TEST_PROPERTY_NAME_THREE));
        assertEquals("property four should be loaded.", TEST_PROPERTY_VALUE_FOUR,
                System.getProperty(TEST_PROPERTY_NAME_FOUR));
        assertEquals("property five should be loaded.", TEST_PROPERTY_VALUE_FIVE,
                System.getProperty(TEST_PROPERTY_NAME_FIVE));
    }

    @Test
    public void testLoadWithInvalidFile() {

        new GlobalPropertyLoader(invalidStream).load();

        assertEquals("property one should be loaded.", INVALID_PROPERTY_VALUE,
                System.getProperty(INVALID_PROPERTY_NAME));
    }

    @Test(expected = RuntimeException.class)
    public void testLoadWithClosedStream() throws IOException {

        stream.close();

        new GlobalPropertyLoader(stream).load();
    }
}
