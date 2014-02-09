package shiver.me.timbers.javascript;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.FileUtils.testTxtFile;
import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class LazyJavaScriptWrappedTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() throws Exception {

        new LazyJavaScriptWrappedTransformer();
    }

    @Test
    public void testTransform() throws Exception {

        assertNotNull("the transform should produce a string.",
                new LazyJavaScriptWrappedTransformer().transform(testTxtFile()));

        assertNotNull("the transform should produce a string.",
                new LazyJavaScriptWrappedTransformer().transform(testTxtFile(), new IterableTokenTransformations()));
    }
}
