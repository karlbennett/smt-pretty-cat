package shiver.me.timbers;

import shiver.me.timbers.properties.PropertyConfiguration;

/**
 * This class contains common test constants and helper methods.
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static final PropertyConfiguration CONFIGURATION = new PropertyConfiguration("config.properties");

    public static final String TEST_PROPERTIES_FILE = "test.properties";

    public static final String TEST_STRING = "test_string";

    public static final String TEST_PREFIX = "test";

    public static final String TEST_PROPERTY_SUFFIX_THREE = "three";

    public static final String TEST_PROPERTY_NAME_ONE = TEST_PREFIX + "." + TestClassOne.class.getSimpleName();
    public static final String TEST_PROPERTY_NAME_TWO = TEST_PREFIX + "." + TestClassTwo.class.getSimpleName();
    public static final String TEST_PROPERTY_NAME_THREE = TEST_PREFIX + "." + TEST_PROPERTY_SUFFIX_THREE;
    public static final String TEST_PROPERTY_NAME_FOUR = TestClassFour.class.getSimpleName();
    public static final String TEST_PROPERTY_NAME_FIVE = TEST_PREFIX + "." + TestClassFive.class.getSimpleName();

    public static final String TEST_PROPERTY_VALUE_ONE = "test value one";
    public static final String TEST_PROPERTY_VALUE_TWO = "test value two";
    public static final String TEST_PROPERTY_VALUE_THREE = "test value three";
    public static final String TEST_PROPERTY_VALUE_FOUR = "test value four";
    public static final String TEST_PROPERTY_VALUE_FIVE = "test value five";
    public static final String TEST_PROPERTY_VALUE_SIX = "test value six";

    public static void clearTestProperties() {

        System.clearProperty(TEST_PROPERTY_NAME_ONE);
        System.clearProperty(TEST_PROPERTY_NAME_TWO);
        System.clearProperty(TEST_PROPERTY_NAME_THREE);
        System.clearProperty(TEST_PROPERTY_NAME_FOUR);
        System.clearProperty(TEST_PROPERTY_NAME_FIVE);
    }

    public static class TestClassOne {
    }

    public static class TestClassTwo {
    }

    public static class TestClassThree {
    }

    public static class TestClassFour {
    }

    public static class TestClassFive {
    }
}
