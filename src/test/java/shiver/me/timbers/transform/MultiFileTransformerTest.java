package shiver.me.timbers.transform;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;
import shiver.me.timbers.transform.composite.WrappedFileTransformer;

import javax.activation.MimeType;
import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.BACKGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.WHITE;
import static shiver.me.timbers.FileUtils.TEST_JAVA_FILE;
import static shiver.me.timbers.FileUtils.testFile;
import static shiver.me.timbers.FileUtils.testTxtFile;
import static shiver.me.timbers.transform.java.JavaTransformer.TEXT_X_JAVA_SOURCE;

public class MultiFileTransformerTest {

    private static final String TEST_STRING = "test string";
    private static final String RESULT_STRING = BLACK.escapeSequence() + WHITE.escapeSequence() + TEST_STRING;

    private WrappedFileTransformer<TokenTransformation> transformer;
    private Transformers<CompositeFileTransformer<TokenTransformation>> transformers;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {

        transformer = mock(WrappedFileTransformer.class);
        when(transformer.transform(any(File.class))).thenReturn(TEST_STRING);

        transformers = mock(Transformers.class);
    }

    @Test
    public void testCreate() {

        new MultiFileTransformer(transformers, BLACK, WHITE);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullTransformers() {

        new MultiFileTransformer(null, BLACK, WHITE);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullBackgroundColour() {

        new MultiFileTransformer(transformers, null, WHITE);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullForegroundColour() {

        new MultiFileTransformer(transformers, BLACK, null);
    }

    @Test
    public void testTransform() {

        final File TEST_FILE = testFile(TEST_JAVA_FILE);

        assertTransform(TEXT_X_JAVA_SOURCE, TEST_FILE);
    }

    @Test
    public void testTransformWithUnknownFileType() {

        final File TEST_FILE = testTxtFile();

        assertTransform(null, TEST_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void testTransformWithInvalidFile() {

        new MultiFileTransformer(transformers, BLACK, WHITE).transform(new File("this file is invalid"));
    }

    @Test(expected = NullPointerException.class)
    public void testTransformWithNullFile() {

        new MultiFileTransformer(transformers, BLACK, WHITE).transform(null);
    }

    public void assertTransform(MimeType mimeType, File testFile) {

        when(transformers.get(mimeType)).thenReturn(transformer);

        assertEquals("the transformed string should be correct.", RESULT_STRING,
                new MultiFileTransformer(transformers, BLACK, WHITE).transform(testFile));

        verify(transformers, times(1)).get(mimeType);
        verify(transformer, times(1)).transform(testFile);

        verifyNoMoreInteractions(transformers);
        verifyNoMoreInteractions(transformer);
    }
}
