package shiver.me.timbers.properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import static shiver.me.timbers.properties.PropertyConfiguration.DEFAULT_THEME;
import static shiver.me.timbers.properties.PropertyConfiguration.DEFAULT_THEME_FILE;
import static shiver.me.timbers.properties.PropertyConfiguration.THEME;
import static shiver.me.timbers.properties.PropertyConfiguration.findThemeFileName;
import static shiver.me.timbers.properties.PropertyConfiguration.rootResourceAsStream;

public class PropertyConfigurationTest {

    private static final String TEST_THEME = "test";
    private static final String TEST_THEME_FILE_NAME = "testTheme.properties";
    private static final String TEST_COLOUR_PROPERTY_NAME = "testColour";
    private static final String TEST_COLOUR_PROPERTY_VALUE = "splorange";

    private static final String INVALID_FILE_NAME = "this file should not exist";

    @Before
    public void setUp() {

        System.setProperty(THEME, TEST_THEME);
        System.setProperty(TEST_THEME, TEST_THEME_FILE_NAME);
    }

    @After
    public void tearDown() {

        clearTestProperties();

        System.clearProperty(THEME);
        System.clearProperty(TEST_THEME);
        System.clearProperty(TEST_COLOUR_PROPERTY_NAME);
    }

    @Test
    public void testCreate() {

        new PropertyConfiguration(TEST_PROPERTIES_FILE);

        assertEquals("property one should be returned.", TEST_PROPERTY_VALUE_ONE,
                System.getProperty(TEST_PROPERTY_NAME_ONE));

        assertEquals("property two should be returned.", TEST_PROPERTY_VALUE_TWO,
                System.getProperty(TEST_PROPERTY_NAME_TWO));

        assertEquals("property three should be returned.", TEST_PROPERTY_VALUE_THREE,
                System.getProperty(TEST_PROPERTY_NAME_THREE));

        assertEquals("property four should be returned.", TEST_PROPERTY_VALUE_FOUR,
                System.getProperty(TEST_PROPERTY_NAME_FOUR));

        assertEquals("property five should be returned.", TEST_PROPERTY_VALUE_FIVE,
                System.getProperty(TEST_PROPERTY_NAME_FIVE));

        assertEquals("the test colour should be set.", TEST_COLOUR_PROPERTY_VALUE,
                System.getProperty(TEST_COLOUR_PROPERTY_NAME));
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithInvalidFileName() {

        new PropertyConfiguration(INVALID_FILE_NAME);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullFileName() {

        new PropertyConfiguration(null);
    }

    @Test
    public void testLoad() {

        PropertyConfiguration.load(TEST_PROPERTIES_FILE);

        assertEquals("property one should be returned.", TEST_PROPERTY_VALUE_ONE,
                System.getProperty(TEST_PROPERTY_NAME_ONE));

        assertEquals("property two should be returned.", TEST_PROPERTY_VALUE_TWO,
                System.getProperty(TEST_PROPERTY_NAME_TWO));

        assertEquals("property three should be returned.", TEST_PROPERTY_VALUE_THREE,
                System.getProperty(TEST_PROPERTY_NAME_THREE));

        assertEquals("property four should be returned.", TEST_PROPERTY_VALUE_FOUR,
                System.getProperty(TEST_PROPERTY_NAME_FOUR));

        assertEquals("property five should be returned.", TEST_PROPERTY_VALUE_FIVE,
                System.getProperty(TEST_PROPERTY_NAME_FIVE));

        assertNull("the test colour should not be set.", System.getProperty(TEST_COLOUR_PROPERTY_NAME));
    }

    @Test(expected = AssertionError.class)
    public void testLoadWithInvalidFileName() {

        PropertyConfiguration.load(INVALID_FILE_NAME);
    }

    @Test(expected = NullPointerException.class)
    public void testLoadWithNullFileName() {

        PropertyConfiguration.load(null);
    }

    @Test
    public void testFindThemeFileName() {

        final String themeFileName = findThemeFileName();

        assertEquals("the theme file name should be correct.", TEST_THEME_FILE_NAME, themeFileName);
    }

    @Test
    public void testFindThemeFileNameWithDarkThemeSet() {

        tearDown();

        System.setProperty(DEFAULT_THEME, TEST_THEME_FILE_NAME);

        final String themeFileName = findThemeFileName();

        assertEquals("the theme file name should be correct.", TEST_THEME_FILE_NAME, themeFileName);
    }

    @Test
    public void testFindThemeFileNameWithNoThemeSet() {

        tearDown();

        final String themeFileName = findThemeFileName();

        assertEquals("the theme file name should be the default.", DEFAULT_THEME_FILE, themeFileName);
    }

    @Test
    public void testRootResourceAsStream() throws IOException {

        InputStream stream = null;

        try {

            stream = rootResourceAsStream(TEST_PROPERTIES_FILE);

            assertNotNull("a stream should be produce.", stream);

        } finally {

            if (null != stream) {

                stream.close();
            }
        }
    }

    @Test
    public void testRootResourceAsStreamWithInvalidFileName() throws IOException {

        assertNull("a stream should not be produce.", rootResourceAsStream(INVALID_FILE_NAME));
    }

    @Test(expected = NullPointerException.class)
    public void testRootResourceAsStreamWithNullFileName() throws IOException {

        rootResourceAsStream(null);
    }
}
