package shiver.me.timbers.java;

import org.antlr.v4.runtime.Token;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.antlr4.java.JavaParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.TestUtils.TEST_STRING;

public class IsAtApplierTest {

    private TokenApplier applier;

    @Before
    public void setUp() throws Exception {

        applier = mock(TokenApplier.class);
    }

    @Test
    public void testCreate() {

        new IsAtApplier(applier);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullColour() {

        new IsAtApplier(null);
    }

    @Test
    public void testApplyWithAtType() {

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(JavaParser.AT);

        new IsAtApplier(applier).apply(null, token, TEST_STRING);

        verify(applier, times(1)).apply(null, token, TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test
    public void testApplyWithNoType() {

        new IsAtApplier(applier).apply(null, mock(Token.class), TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyWithNullToken() {

        new IsAtApplier(applier).apply(null, null, TEST_STRING);
    }
}
