package shiver.me.timbers.java;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class LazyJavaWrappedTransformerTest {

    @Test
    public void testCreate() throws Exception {

        new LazyJavaWrappedTransformer();
    }

    @Test
    public void testTransform() throws Exception {

        assertNotNull("the transform should produce a string.",
                new LazyJavaWrappedTransformer().transform(testTxtFile()));

        assertNotNull("the transform should produce a string.",
                new LazyJavaWrappedTransformer().transform(testTxtFile(), new IterableTokenTransformations()));
    }
}
