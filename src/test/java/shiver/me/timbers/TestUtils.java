package shiver.me.timbers;

/**
 * This class contains common test constants and helper methods.
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static final String TEST_STRING = "test_string";

    public static final String TEST_PREFIX = "test";

    public static final String TEST_PROPERTY_SUFFIX_THREE = "two";

    public static final String TEST_PROPERTY_NAME_ONE = TEST_PREFIX + "." + TestClassOne.class.getSimpleName();
    public static final String TEST_PROPERTY_NAME_TWO = TEST_PREFIX + "." + TestClassTwo.class.getSimpleName();
    public static final String TEST_PROPERTY_NAME_THREE = TEST_PREFIX + "." + TEST_PROPERTY_SUFFIX_THREE;
    public static final String TEST_PROPERTY_NAME_FOUR = TestClassFour.class.getSimpleName();

    public static class TestClassOne {
    }

    public static class TestClassTwo {
    }

    public static class TestClassThree {
    }

    public static class TestClassFour {
    }
}
