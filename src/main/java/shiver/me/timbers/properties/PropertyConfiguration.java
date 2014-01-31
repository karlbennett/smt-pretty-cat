package shiver.me.timbers.properties;

import java.io.InputStream;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * Instantiating this class will load all the pretty cat properties.
 */
public class PropertyConfiguration {

    public static final String THEME = "theme";
    public static final String DEFAULT_THEME = "dark";
    public static final String DEFAULT_THEME_FILE = "dark.properties";

    public PropertyConfiguration(String configFile) {

        assertIsNotNull(argumentIsNullMessage("configFile"), configFile);

        load(configFile);

        final String themeFile = findThemeFileName();

        load(themeFile);
    }

    public static void load(String configFile) {

        final InputStream stream = rootResourceAsStream(configFile);

        new GlobalPropertyLoader(stream).load();
    }

    public static String findThemeFileName() {

        final String theme = System.getProperty(THEME, DEFAULT_THEME);

        return System.getProperty(theme, DEFAULT_THEME_FILE);
    }

    public static InputStream rootResourceAsStream(String fileName) {

        return PropertyConfiguration.class.getClassLoader().getResourceAsStream(fileName);
    }
}
