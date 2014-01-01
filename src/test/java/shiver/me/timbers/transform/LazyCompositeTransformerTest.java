package shiver.me.timbers.transform;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
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

    private InputStream stream;
    private Transformations<Transformation> transformations;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {

        stream = mock(InputStream.class);
        transformations = mock(Transformations.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreate() {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable);

        assertNull("the callable should not have produced a transformer.", callable.getTransformer());

        verifyZeroInteractions(callable);
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithCallableThatThrowsAnException() throws Exception {

        final Callable<CompositeTransformer<Transformation>> callable = mock(Callable.class);
        when(callable.call()).thenThrow(new Exception("test callable exception."));

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(mock(InputStream.class));
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullCallable() {

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(null);
    }

    @Test
    public void testTransformWithInputStreamAndTransformations() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(stream, transformations);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(1)).transform(stream, transformations);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithInputStreamAndTransformationsTwice() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        final CompositeTransformer<Transformation> transformer =
                new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable);
        transformer.transform(stream, transformations);
        transformer.transform(stream, transformations);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(2)).transform(stream, transformations);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNullInputStreamAndNotNullTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(null, transformations);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null, transformations);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNotNullInputStreamAndNullTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(stream, null);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(stream, null);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformWithNullInputStreamAndTransformations() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(null, null);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null, null);

        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithInputStream() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable)
                .transform(stream);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(1)).transform(stream);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithInputStreamTwice() throws Exception {

        final MockTransformerCallable callable = spy(new MockTransformerCallable());

        final CompositeTransformer<Transformation> transformer =
                new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable);
        transformer.transform(stream);
        transformer.transform(stream);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(callable, times(1)).call();
        verify(mockTransformer, times(2)).transform(stream);

        verifyNoMoreInteractions(callable);
        verifyNoMoreInteractions(mockTransformer);
    }

    @Test
    public void testTransformInputStreamWithNullInputStream() throws Exception {

        final MockTransformerCallable callable = new MockTransformerCallable();

        new LazyCompositeTransformer<CompositeTransformer<Transformation>, Transformation>(callable).transform(null);

        final CompositeTransformer<Transformation> mockTransformer = callable.getTransformer();

        verify(mockTransformer, times(1)).transform(null);

        verifyNoMoreInteractions(mockTransformer);
    }

    private static class MockTransformerCallable implements Callable<CompositeTransformer<Transformation>> {

        private CompositeTransformer<Transformation> transformer;

        @SuppressWarnings("unchecked")
        @Override
        public CompositeTransformer<Transformation> call() throws Exception {

            return transformer = mock(CompositeTransformer.class);
        }

        private CompositeTransformer<Transformation> getTransformer() {

            return transformer;
        }
    }
}
