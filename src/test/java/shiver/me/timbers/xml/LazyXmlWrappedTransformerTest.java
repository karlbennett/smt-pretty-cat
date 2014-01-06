package shiver.me.timbers.xml;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.iterable.IterableTransformations;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.FileUtils.testTxtFile;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;

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
                new LazyXmlWrappedTransformer().transform(testTxtFile(),
                        new IterableTransformations<TokenTransformation>(NULL_TOKEN_TRANSFORMATION)));
    }
}
