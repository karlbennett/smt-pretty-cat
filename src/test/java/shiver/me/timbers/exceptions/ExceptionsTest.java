package shiver.me.timbers.exceptions;


import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.Container;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class ExceptionsTest {

    private ExceptionHandler exceptionHandler;
    private Callable<Void> callable;
    private Container<Class, ExceptionHandler> handlers;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {

        exceptionHandler = mock(ExceptionHandler.class);
        callable = mock(Callable.class);
        handlers = mock(Container.class);
    }

    @Test
    public void testCreate() throws Exception {

        final Constructor<Exceptions> constructor = Exceptions.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testWithExceptionHandlingWithNoException() throws Throwable {

        assertTrue("should return success status code.", Exceptions.withExceptionHandling(handlers, callable));

        verify(callable, times(1)).call();
        verifyNoMoreInteractions(callable);

        verifyZeroInteractions(handlers);

        verifyZeroInteractions(exceptionHandler);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithExceptionHandlingWithHandledException() throws Throwable {

        final Exception exception = new Exception();

        when(callable.call()).thenThrow(exception);

        when(handlers.get(exception.getClass())).thenReturn(exceptionHandler);

        assertFalse("should return failed status code.", Exceptions.withExceptionHandling(handlers, callable));

        verify(callable, times(1)).call();
        verifyNoMoreInteractions(callable);

        verify(handlers, times(1)).get(exception.getClass());
        verifyNoMoreInteractions(handlers);

        verify(exceptionHandler, times(1)).handle(exception);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithExceptionHandlingWithHandledError() throws Throwable {

        final Error error = new Error();

        when(callable.call()).thenThrow(error);

        when(handlers.get(error.getClass())).thenReturn(exceptionHandler);

        assertFalse("should return failed status code.", Exceptions.withExceptionHandling(handlers, callable));

        verify(callable, times(1)).call();
        verifyNoMoreInteractions(callable);

        verify(handlers, times(1)).get(error.getClass());
        verifyNoMoreInteractions(handlers);

        verify(exceptionHandler, times(1)).handle(error);
        verifyNoMoreInteractions(exceptionHandler);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithExceptionHandlingWithNullHandlersAndNoException() throws Throwable {

        assertTrue("should return success status code.", Exceptions.withExceptionHandling(null, callable));

        verify(callable, times(1)).call();
        verifyNoMoreInteractions(callable);
    }

    @Test(expected = NullPointerException.class)
    public void testWithExceptionHandlingWithNullHandlersAndException() throws Throwable {

        final Exception exception = new Exception();

        when(callable.call()).thenThrow(exception);

        Exceptions.withExceptionHandling(null, callable);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithExceptionHandlingWithNullCallable() throws Throwable {

        when(handlers.get(NullPointerException.class)).thenReturn(exceptionHandler);

        assertFalse("should return failed status code.", Exceptions.withExceptionHandling(handlers, null));

        verify(handlers, times(1)).get(NullPointerException.class);
        verifyNoMoreInteractions(handlers);

        verify(exceptionHandler, times(1)).handle(isA(NullPointerException.class));
        verifyNoMoreInteractions(exceptionHandler);
    }
}
