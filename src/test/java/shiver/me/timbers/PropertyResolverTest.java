package shiver.me.timbers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static shiver.me.timbers.TestUtils.TEST_PREFIX;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_FOUR;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_ONE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_THREE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_NAME_TWO;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_SUFFIX_THREE;
import static shiver.me.timbers.TestUtils.TestClassFour;
import static shiver.me.timbers.TestUtils.TestClassOne;
import static shiver.me.timbers.TestUtils.TestClassThree;
import static shiver.me.timbers.TestUtils.TestClassTwo;

public class PropertyResolverTest {

    private static final String TEST_PROPERTY_VALUE_ONE = "test value one";
    private static final String TEST_PROPERTY_VALUE_TWO = "test value two";
    private static final String TEST_PROPERTY_VALUE_THREE = "test value three";
    private static final String TEST_PROPERTY_VALUE_FOUR = "test value four";
    private static final String TEST_DEFAULT_PROPERTY_VALUE = "test default value";

    @Before
    public void setUp() throws Exception {

        System.setProperty(TEST_PROPERTY_NAME_ONE, TEST_PROPERTY_VALUE_ONE);
        System.setProperty(TEST_PROPERTY_NAME_TWO, TEST_PROPERTY_VALUE_TWO);
        System.setProperty(TEST_PROPERTY_NAME_THREE, TEST_PROPERTY_VALUE_THREE);
        System.setProperty(TEST_PROPERTY_NAME_FOUR, TEST_PROPERTY_VALUE_FOUR);
    }

    @After
    public void tearDown() throws Exception {

        System.clearProperty(TEST_PROPERTY_NAME_ONE);
        System.clearProperty(TEST_PROPERTY_NAME_TWO);
        System.clearProperty(TEST_PROPERTY_NAME_THREE);
        System.clearProperty(TEST_PROPERTY_NAME_FOUR);
    }

    @Test
    public void testCreate() {

        new PropertyResolver();

        new PropertyResolver(TEST_PREFIX);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNull() {

        new PropertyResolver(null);
    }

    @Test
    public void testResolve() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertEquals("property one should be returned.", TEST_PROPERTY_VALUE_ONE,
                resolver.resolve(TestClassOne.class, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("property two should be returned.", TEST_PROPERTY_VALUE_TWO,
                resolver.resolve(TestClassTwo.class, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("property three should be returned.", TEST_PROPERTY_VALUE_THREE,
                resolver.resolve(TEST_PROPERTY_SUFFIX_THREE, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TestClassFour.class, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithNoPrefix() {

        ValueResolver<String> resolver = new PropertyResolver();

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TestClassOne.class, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TestClassTwo.class, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TEST_PROPERTY_SUFFIX_THREE, TEST_DEFAULT_PROPERTY_VALUE));

        assertEquals("property four should be returned.", TEST_PROPERTY_VALUE_FOUR,
                resolver.resolve(TestClassFour.class, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithInvalidPrefix() {

        ValueResolver<String> resolver = new PropertyResolver("invalid prefix");

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TestClassOne.class, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithInvalidLookupType() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve(TestClassThree.class, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithNullType() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve((Class) null, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithNullName() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertEquals("the default value should be returned.", TEST_DEFAULT_PROPERTY_VALUE,
                resolver.resolve((String) null, TEST_DEFAULT_PROPERTY_VALUE));
    }

    @Test
    public void testResolveWithNullDefaultValue() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertEquals("property one should be returned.", TEST_PROPERTY_VALUE_ONE, resolver.resolve(TestClassOne.class,
                null));

        assertEquals("property one should be returned.", TEST_PROPERTY_VALUE_THREE,
                resolver.resolve(TEST_PROPERTY_SUFFIX_THREE, null));

        assertNull("null should be returned.", resolver.resolve(Object.class, null));
    }
}
