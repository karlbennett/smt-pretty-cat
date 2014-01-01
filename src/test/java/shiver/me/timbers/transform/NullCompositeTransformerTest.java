package shiver.me.timbers.transform;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FileUtils.testTxtContents;
import static shiver.me.timbers.FileUtils.testTxtInputStream;

public class NullCompositeTransformerTest {

    @Test
    public void testCreate() {

        new NullCompositeTransformer();
    }

    @Test
    public void testTransformWithInputStrea() {

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullCompositeTransformer().transform(testTxtInputStream()));
    }

    @Test
    public void testTransformWithString() {

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullCompositeTransformer().transform(testTxtContents()));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithClosedInputStream() throws IOException {

        final InputStream stream = testTxtInputStream();
        stream.close();

        new NullCompositeTransformer().transform(stream);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        new NullCompositeTransformer().transform((InputStream) null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullString() {

        new NullCompositeTransformer().transform((String) null);
    }
}
