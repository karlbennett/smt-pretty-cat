package shiver.me.timbers.xml;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class LazyXmlWrappedTransformerTest {

    @Test
    public void testCreate() throws Exception {

        new LazyXmlWrappedTransformer();
    }

    @Test
    public void testTransform() throws Exception {

        assertNotNull("the transform should produce a string.",
                new LazyXmlWrappedTransformer().transform(testTxtFile()));

        assertNotNull("the transform should produce a string.",
                new LazyXmlWrappedTransformer().transform(testTxtFile(), new IterableTokenTransformations()));
    }
}
