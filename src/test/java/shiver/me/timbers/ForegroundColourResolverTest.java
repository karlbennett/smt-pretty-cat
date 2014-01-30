package shiver.me.timbers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.RED;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.TestUtils.TEST_PREFIX;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_FOUR;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_ONE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_THREE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_TWO;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_SUFFIX_THREE;
import static shiver.me.timbers.TestUtils.TestClassFour;
import static shiver.me.timbers.TestUtils.TestClassOne;
import static shiver.me.timbers.TestUtils.TestClassTwo;
import static shiver.me.timbers.TestUtils.TestClassThree;

public class ForegroundColourResolverTest {

    private static final String TEST_PROPERTY_NAME_FIVE = TEST_PREFIX + "." + TestClassFive.class.getSimpleName();
    private static final String TEST_PROPERTY_VALUE_FIVE = "not a colour";

    @Before
    public void setUp() throws Exception {

        System.setProperty(TEST_PROPERTY_NAME_ONE, RED.name());
        System.setProperty(TEST_PROPERTY_NAME_TWO, GREEN.name());
        System.setProperty(TEST_PROPERTY_NAME_THREE, BLUE.name());
        System.setProperty(TEST_PROPERTY_NAME_FOUR, YELLOW.name());
        System.setProperty(TEST_PROPERTY_NAME_FIVE, TEST_PROPERTY_VALUE_FIVE);
    }

    @After
    public void tearDown() throws Exception {

        System.clearProperty(TEST_PROPERTY_NAME_ONE);
        System.clearProperty(TEST_PROPERTY_NAME_TWO);
        System.clearProperty(TEST_PROPERTY_NAME_THREE);
        System.clearProperty(TEST_PROPERTY_NAME_FOUR);
        System.clearProperty(TEST_PROPERTY_NAME_FIVE);
    }

    @Test
    public void testCreate() {

        new ForegroundColourResolver();

        new ForegroundColourResolver(TEST_PREFIX);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNull() {

        new ForegroundColourResolver(null);
    }

    @Test
    public void testResolve() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        assertEquals("property one should be returned.", RED, resolver.resolve(TestClassOne.class, BLACK));

        assertEquals("property two should be returned.", GREEN, resolver.resolve(TestClassTwo.class, BLACK));

        assertEquals("property three should be returned.", BLUE, resolver.resolve(TEST_PROPERTY_SUFFIX_THREE, BLACK));

        assertEquals("the default value should be returned.", BLACK, resolver.resolve(TestClassFour.class, BLACK));
    }

    @Test
    public void testResolveWithNoPrefix() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver();

        assertEquals("the default value should be returned.", BLACK, resolver.resolve(TestClassOne.class, BLACK));

        assertEquals("the default value should be returned.", BLACK, resolver.resolve(TestClassTwo.class, BLACK));

        assertEquals("the default value should be returned.", BLACK,
                resolver.resolve(TEST_PROPERTY_SUFFIX_THREE, BLACK));

        assertEquals("property four should be returned.", YELLOW, resolver.resolve(TestClassFour.class, BLACK));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResolveWithInvalidPropertyValue() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        resolver.resolve(TestClassFive.class, BLACK);
    }

    @Test
    public void testResolveWithInvalidPrefix() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver("invalid prefix");

        assertEquals("the default value should be returned.", BLACK,
                resolver.resolve(TestClassOne.class, BLACK));
    }

    @Test
    public void testResolveWithInvalidLookupType() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", BLACK, resolver.resolve(TestClassThree.class, BLACK));
    }

    @Test
    public void testResolveWithNullType() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", BLACK, resolver.resolve((Class) null, BLACK));
    }

    @Test
    public void testResolveWithNullName() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", BLACK, resolver.resolve((String) null, BLACK));
    }

    @Test(expected = NullPointerException.class)
    public void testResolveWithNullDefaultValue() {

        ValueResolver<FOREGROUND_COLOUR> resolver = new ForegroundColourResolver(TEST_PREFIX);

        resolver.resolve(TestClassOne.class, null);
    }

    public static class TestClassFive {
    }
}
