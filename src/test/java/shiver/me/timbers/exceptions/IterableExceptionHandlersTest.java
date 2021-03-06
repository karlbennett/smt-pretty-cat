package shiver.me.timbers.exceptions;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class IterableExceptionHandlersTest {

    private ExceptionHandler<Exception> exceptionExceptionHandler;
    private ExceptionHandler<RuntimeException> runtimeExceptionExceptionHandler;
    private ExceptionHandler<Error> errorExceptionHandler;
    private ExceptionHandler<AssertionError> assertionErrorExceptionHandler;

    private List<ExceptionHandler> handlers;

    private ExceptionHandler nullHandler;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {

        exceptionExceptionHandler = new TestExceptionHandler<Exception>(Exception.class);
        runtimeExceptionExceptionHandler = new TestExceptionHandler<RuntimeException>(RuntimeException.class);
        errorExceptionHandler = new TestExceptionHandler<Error>(Error.class);
        assertionErrorExceptionHandler = new TestExceptionHandler<AssertionError>(AssertionError.class);

        nullHandler = mock(ExceptionHandler.class);

        handlers = spy(new LinkedList<ExceptionHandler>() {{
            add(exceptionExceptionHandler);
            add(runtimeExceptionExceptionHandler);
            add(errorExceptionHandler);
            add(assertionErrorExceptionHandler);
        }});
    }

    @Test
    public void testCreateWithNullHandler() throws Exception {

        new IterableExceptionHandlers(nullHandler);

        verifyZeroInteractions(nullHandler);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullNullHandler() throws Exception {

        new IterableExceptionHandlers(null);
    }

    @Test
    public void testCreateWithHandlersAndNullHandler() throws Exception {

        new IterableExceptionHandlers(handlers, nullHandler);

        verifyZeroInteractions(handlers);

        verifyZeroInteractions(nullHandler);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullHandlersAndNullHandler() throws Exception {

        new IterableExceptionHandlers(null, nullHandler);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithHandlersAndNullNullHandler() throws Exception {

        new IterableExceptionHandlers(handlers, null);
    }

    @Test
    public void testGetWithValidIndices() throws Exception {

        final IterableExceptionHandlers exceptionHandlers = new IterableExceptionHandlers(handlers, nullHandler);

        assertEquals("the requested handler should be correct.", exceptionExceptionHandler, exceptionHandlers.get(0));

        assertEquals("the requested handler should be correct.", runtimeExceptionExceptionHandler,
                exceptionHandlers.get(1));

        assertEquals("the requested handler should be correct.", errorExceptionHandler, exceptionHandlers.get(2));

        assertEquals("the requested handler should be correct.", assertionErrorExceptionHandler,
                exceptionHandlers.get(3));

        verify(handlers, times(1)).iterator();

        verifyZeroInteractions(nullHandler);
    }

    @Test
    public void testGetWithInvalidIndex() throws Exception {

        final IterableExceptionHandlers exceptionHandlers = new IterableExceptionHandlers(handlers, nullHandler);

        assertEquals("the null handler should be returned for a negative index.", nullHandler,
                exceptionHandlers.get(-1));

        assertEquals("the null handler should be returned for an index that is too large.", nullHandler,
                exceptionHandlers.get(handlers.size()));

        verify(handlers, times(1)).iterator();

        verifyZeroInteractions(nullHandler);

    }

    @Test
    public void testGetWithValidKeys() throws Exception {

        final IterableExceptionHandlers exceptionHandlers = new IterableExceptionHandlers(handlers, nullHandler);

        assertEquals("the requested handler should be correct.", exceptionExceptionHandler,
                exceptionHandlers.get(Exception.class));

        assertEquals("the requested handler should be correct.", runtimeExceptionExceptionHandler,
                exceptionHandlers.get(RuntimeException.class));

        assertEquals("the requested handler should be correct.", errorExceptionHandler,
                exceptionHandlers.get(Error.class));

        assertEquals("the requested handler should be correct.", assertionErrorExceptionHandler,
                exceptionHandlers.get(AssertionError.class));

        verify(handlers, times(1)).iterator();

        verifyZeroInteractions(nullHandler);
    }

    @Test
    public void testGetWithNullKey() throws Exception {

        assertEquals("the requested handler should be correct.", nullHandler,
                new IterableExceptionHandlers(handlers, nullHandler).get(null));

        verify(handlers, times(1)).iterator();

        verifyZeroInteractions(nullHandler);
    }

    @Test
    public void testGetWithInvalidKeys() throws Exception {

        final IterableExceptionHandlers exceptionHandlers = new IterableExceptionHandlers(handlers, nullHandler);

        assertEquals("the null handler should be returned for an invalid key.", nullHandler,
                exceptionHandlers.get(Class.class));

        assertEquals("the null handler should be returned for an invalid key.", nullHandler,
                exceptionHandlers.get(Object.class));

        assertEquals("the null handler should be returned for an invalid key.", nullHandler,
                exceptionHandlers.get(Integer.class));

        verify(handlers, times(1)).iterator();

        verifyZeroInteractions(nullHandler);
    }

    @Test
    public void testIterator() throws Exception {

        int count = 0;
        for (ExceptionHandler handler : new IterableExceptionHandlers(handlers, nullHandler)) {

            assertThat("the iterator should produce the correct items.", handlers, hasItem(handler));

            count++;
        }

        assertEquals("the iterator should produce the correct number of iterations.", handlers.size(), count);

    }

    @Test
    public void testAsCollection() throws Exception {

        final Collection<ExceptionHandler> handlerCollection =
                new IterableExceptionHandlers(handlers, nullHandler).asCollection();

        assertEquals("the collections size should be correct.", handlers.size(), handlerCollection.size());

        assertThat("the returned collection should contain all the correct values.", handlerCollection,
                containsInAnyOrder(handlers.toArray()));
    }

    private static class TestExceptionHandler<T extends Throwable> extends AbstractExceptionHandler<T> {

        protected TestExceptionHandler(Class<T> exceptionType) {
            super(exceptionType);
        }

        @Override
        public int handle(T throwable) throws T {
            return -1;
        }
    }
}
