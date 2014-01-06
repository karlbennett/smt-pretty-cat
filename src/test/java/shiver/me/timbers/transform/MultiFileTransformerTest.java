package shiver.me.timbers.transform;

import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;
import shiver.me.timbers.transform.composite.WrappedFileTransformer;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class MultiFileTransformerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() {

        new MultiFileTransformer(mock(Transformers.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullTransformers() {

        new MultiFileTransformer(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform() {

        final String TEST_STRING = "test string";

        final File TEST_FILE = testTxtFile();

        final WrappedFileTransformer<TokenTransformation> transformer = mock(WrappedFileTransformer.class);
        when(transformer.transform(any(File.class))).thenReturn(TEST_STRING);

        final Transformers<File, CompositeFileTransformer<TokenTransformation>> transformers = mock(Transformers.class);
        when(transformers.get(TEST_FILE)).thenReturn(transformer);

        assertEquals("the transformed string should be correct.", TEST_STRING,
                new MultiFileTransformer(transformers).transform(TEST_FILE));

        verify(transformers, times(1)).get(TEST_FILE);
        verify(transformer, times(1)).transform(notNull(File.class));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithInvalidFile() {

        new MultiFileTransformer(mock(Transformers.class)).transform(new File("this file is invalid"));
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullFile() {

        new MultiFileTransformer(mock(Transformers.class)).transform(null);
    }
}
