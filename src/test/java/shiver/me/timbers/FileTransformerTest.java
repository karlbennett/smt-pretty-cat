package shiver.me.timbers;

import org.junit.Test;
import shiver.me.timbers.transform.WrappedTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FileTransformerTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() {

        new FileTransformer(mock(Transformers.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullTransformers() {

        new FileTransformer(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransform() {

        final String TEST_STRING = "test string";

        final File TEST_FILE = testFile();

        final WrappedTransformer<TokenTransformation> transformer = mock(WrappedTransformer.class);
        when(transformer.transform(any(InputStream.class))).thenReturn(TEST_STRING);

        final Transformers<File, TokenTransformation> transformers = mock(Transformers.class);
        when(transformers.get(TEST_FILE)).thenReturn(transformer);

        assertEquals("the transformed string should be correct.", TEST_STRING,
                new FileTransformer(transformers).transform(TEST_FILE));

        verify(transformers, times(1)).get(TEST_FILE);
        verify(transformer, times(1)).transform(notNull(InputStream.class));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithInvalidFile() {

        new FileTransformer(mock(Transformers.class)).transform(new File("this file is invalid"));
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullFile() {

        new FileTransformer(mock(Transformers.class)).transform(null);
    }

    private static File testFile() {

        final URL url = FileTransformerTest.class.getResource("Test.txt");

        return new File(url.getFile());
    }
}
