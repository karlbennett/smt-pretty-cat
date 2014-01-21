package shiver.me.timbers.exceptions;


import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.Container;

import java.lang.reflect.Constructor;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.exceptions.Exceptions.SUCCESS;
import static shiver.me.timbers.exceptions.Exceptions.withExceptionHandling;

public class ExceptionsTest {

    private static final int ERROR_CODE = 1;

    private ExceptionHandler exceptionHandler;
    private Callable<Void> callable;
    private Container<Class, ExceptionHandler> handlers;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Throwable {

        exceptionHandler = mock(ExceptionHandler.class);
        when(exceptionHandler.handle(any(Throwable.class))).thenReturn(ERROR_CODE);

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

        assertEquals("should return success status code.", SUCCESS, withExceptionHandling(handlers, callable));

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

        assertEquals("should return failed status code.", ERROR_CODE, withExceptionHandling(handlers, callable));

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

        assertEquals("should return failed status code.", ERROR_CODE, withExceptionHandling(handlers, callable));

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

        assertEquals("should return success status code.", SUCCESS, withExceptionHandling(null, callable));

        verify(callable, times(1)).call();
        verifyNoMoreInteractions(callable);
    }

    @Test(expected = NullPointerException.class)
    public void testWithExceptionHandlingWithNullHandlersAndException() throws Throwable {

        final Exception exception = new Exception();

        when(callable.call()).thenThrow(exception);

        withExceptionHandling(null, callable);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testWithExceptionHandlingWithNullCallable() throws Throwable {

        when(handlers.get(NullPointerException.class)).thenReturn(exceptionHandler);

        assertEquals("should return failed status code.", ERROR_CODE, withExceptionHandling(handlers, null));

        verify(handlers, times(1)).get(NullPointerException.class);
        verifyNoMoreInteractions(handlers);

        verify(exceptionHandler, times(1)).handle(isA(NullPointerException.class));
        verifyNoMoreInteractions(exceptionHandler);
    }
}
