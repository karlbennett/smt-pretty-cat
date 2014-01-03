package shiver.me.timbers.transform;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static shiver.me.timbers.FileUtils.testTxtContents;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class NullCompositeTransformerTest {

    @Test
    public void testCreate() {

        new NullCompositeTransformer();
    }

    @Test
    public void testTransformWithInputStream() {

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullCompositeTransformer().transform(testTxtFile()));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithInvalidFile() throws IOException {

        new NullCompositeTransformer().transform(new File("this file is invalid."));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        assertNull("the null value should be passed through.", new NullCompositeTransformer().transform(null));
    }
}
