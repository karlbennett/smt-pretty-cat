package shiver.me.timbers.xml;

import org.junit.Test;
import shiver.me.timbers.transform.IndividualTransformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertNotNull;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;

public class LazyXmlWrappedTransformerTest {

    @Test
    public void testCreate() throws Exception {

        new LazyXmlWrappedTransformer();
    }

    @Test
    public void testTransform() throws Exception {

        assertNotNull("the transform should produce a string.",
                new LazyXmlWrappedTransformer().transform(new ByteArrayInputStream("".getBytes())));

        assertNotNull("the transform should produce a string.",
                new LazyXmlWrappedTransformer().transform(new ByteArrayInputStream("".getBytes()),
                        new IndividualTransformations<TokenTransformation>(NULL_TOKEN_TRANSFORMATION)));
    }
}
