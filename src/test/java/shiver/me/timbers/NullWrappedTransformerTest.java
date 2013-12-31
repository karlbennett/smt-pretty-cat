package shiver.me.timbers;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FileUtils.testInputStream;
import static shiver.me.timbers.FileUtils.testText;

public class NullWrappedTransformerTest {

    @Test
    public void testCreate() {

        new NullWrappedTransformer();
    }

    @Test
    public void testTransformWithInputStrea() {

        assertEquals("the text should not be transformed.", testText(),
                new NullWrappedTransformer().transform(testInputStream()));
    }

    @Test
    public void testTransformWithString() {

        assertEquals("the text should not be transformed.", testText(),
                new NullWrappedTransformer().transform(testText()));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithClosedInputStream() throws IOException {

        final InputStream stream = testInputStream();
        stream.close();

        new NullWrappedTransformer().transform(stream);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        new NullWrappedTransformer().transform((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullString() {

        new NullWrappedTransformer().transform((String) null);
    }
}
