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
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_FOUR;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_ONE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_THREE;
import static shiver.me.timbers.TestUtils.TEST_PROPERTY_VALUE_TWO;
import static shiver.me.timbers.TestUtils.TestClassFour;
import static shiver.me.timbers.TestUtils.TestClassOne;
import static shiver.me.timbers.TestUtils.TestClassThree;
import static shiver.me.timbers.TestUtils.TestClassTwo;
import static shiver.me.timbers.TestUtils.clearTestProperties;

public class PropertyResolverTest {

    @Before
    public void setUp() throws Exception {

        System.setProperty(TEST_PROPERTY_NAME_ONE, TEST_PROPERTY_VALUE_ONE);
        System.setProperty(TEST_PROPERTY_NAME_TWO, TEST_PROPERTY_VALUE_TWO);
        System.setProperty(TEST_PROPERTY_NAME_THREE, TEST_PROPERTY_VALUE_THREE);
        System.setProperty(TEST_PROPERTY_NAME_FOUR, TEST_PROPERTY_VALUE_FOUR);
    }

    @After
    public void tearDown() throws Exception {

        clearTestProperties();
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
                resolver.resolve(TestClassOne.class));

        assertEquals("property two should be returned.", TEST_PROPERTY_VALUE_TWO,
                resolver.resolve(TestClassTwo.class));

        assertEquals("property three should be returned.", TEST_PROPERTY_VALUE_THREE,
                resolver.resolve(TEST_PROPERTY_SUFFIX_THREE));

        assertNull("property five should not be returned.", resolver.resolve(TestClassFour.class));
    }

    @Test
    public void testResolveWithNoPrefix() {

        ValueResolver<String> resolver = new PropertyResolver();

        assertNull("the prefixed property should not be returned.", resolver.resolve(TestClassOne.class));

        assertNull("the prefixed property should not be returned.", resolver.resolve(TestClassTwo.class));

        assertNull("the prefixed property should not be returned.", resolver.resolve(TEST_PROPERTY_SUFFIX_THREE));

        assertEquals("property four should be returned.", TEST_PROPERTY_VALUE_FOUR,
                resolver.resolve(TestClassFour.class));
    }

    @Test
    public void testResolveWithInvalidPrefix() {

        ValueResolver<String> resolver = new PropertyResolver("invalid prefix");

        assertNull("a property should not be returned.", resolver.resolve(TestClassOne.class));
    }

    @Test
    public void testResolveWithInvalidLookupType() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertNull("a property should not be returned.", resolver.resolve(TestClassThree.class));
    }

    @Test
    public void testResolveWithNullType() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertNull("a property should not be returned.", resolver.resolve((Class) null));
    }

    @Test
    public void testResolveWithInvalidLookupName() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertNull("a property should not be returned.", resolver.resolve("invalid property name"));
    }

    @Test
    public void testResolveWithNullName() {

        ValueResolver<String> resolver = new PropertyResolver(TEST_PREFIX);

        assertNull("a property should not be returned.", resolver.resolve((String) null));
    }
}
