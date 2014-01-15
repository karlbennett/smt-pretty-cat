package shiver.me.timbers.transform;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;

import javax.activation.MimeType;
import java.io.File;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class LazyCompositeTransformerTest {

    private MimeType mimeType;
    private File file;
    private Transformations<Transformation> transformations;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {

        mimeType = mock(MimeType.class);
        file = mock(File.class);
        transformations = mock(Transformations.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable);

        assertNull("the callable should not have produced a transformer.", callable.getTransformer());

        verifyZeroInteractions(callable);
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithCallableThatThrowsAnException() throws Exception {

        final Callable<CompositeFileTransformer<Transformation>> callable = mock(Callable.class);
        when(callable.call()).thenThrow(new Exception("test callable exception."));

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(mock(File.class));
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullCallable() {

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, null);
    }

    @Test
    public void testTransformWithInputStreamAndTransformations() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(file, transformations);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(1)).transform(file, transformations);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithInputStreamAndTransformationsTwice() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        final CompositeFileTransformer<Transformation> transformer =
                new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType,
                        callable);
        transformer.transform(file, transformations);
        transformer.transform(file, transformations);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(2)).transform(file, transformations);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNullInputStreamAndNotNullTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(null, transformations);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null, transformations);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNotNullInputStreamAndNullTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(file, null);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(file, null);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNullInputStreamAndTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(null, null);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null, null);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithInputStream() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(file);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(1)).transform(file);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithInputStreamTwice() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        final CompositeFileTransformer<Transformation> transformer =
                new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType,
                        callable);
        transformer.transform(file);
        transformer.transform(file);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(2)).transform(file);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithNullInputStream() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeFileTransformer<Transformation>, Transformation>(mimeType, callable)
                .transform(null);

        final CompositeFileTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null);

        verifyNoMoreInteractions(mockTransformer);
    }

    private static class MockTransformerCallable implements Callable<CompositeFileTransformer<Transformation>> {

        private CompositeFileTransformer<Transformation> transformer;

        @SuppressWarnings("unchecked")
        @Override
        public CompositeFileTransformer<Transformation> call() throws Exception {

            return transformer = mock(CompositeFileTransformer.class);
        }

        private CompositeFileTransformer<Transformation> getTransformer() {

            return transformer;
        }
    }
}
