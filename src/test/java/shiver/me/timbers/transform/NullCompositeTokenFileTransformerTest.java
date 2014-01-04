package shiver.me.timbers.transform;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static shiver.me.timbers.FileUtils.testTxtContents;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class NullCompositeTokenFileTransformerTest {

    @Test
    public void testCreate() {

        new NullCompositeTokenFileTransformer();
    }

    @Test
    public void testTransformWithInputStream() {

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullCompositeTokenFileTransformer().transform(testTxtFile()));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithInvalidFile() throws IOException {

        new NullCompositeTokenFileTransformer().transform(new File("this file is invalid."));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        assertNull("the null value should be passed through.", new NullCompositeTokenFileTransformer().transform(null));
    }
}
