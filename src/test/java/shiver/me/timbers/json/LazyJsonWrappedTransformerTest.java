package shiver.me.timbers.json;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.FileUtils.testTxtFile;
import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class LazyJsonWrappedTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() throws Exception {

        new LazyJsonWrappedTransformer();
    }

    @Test
    public void testTransform() throws Exception {

        assertNotNull("the transform should produce a string.",
                new LazyJsonWrappedTransformer().transform(testTxtFile()));

        assertNotNull("the transform should produce a string.",
                new LazyJsonWrappedTransformer().transform(testTxtFile(), new IterableTokenTransformations()));
    }
}
