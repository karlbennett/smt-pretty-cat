package shiver.me.timbers.clojure;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.antlr4.clojure.ClojureParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.TestUtils.TEST_STRING;

public class IsCollectionSymbolApplierTest {

    private TokenApplier applier;

    @Before
    public void setUp() throws Exception {

        applier = mock(TokenApplier.class);
    }

    @Test
    public void testCreate() {

        new IsCollectionSymbolApplier(applier);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullColour() {

        new IsCollectionSymbolApplier(null);
    }

    @Test
    public void testApplyWithSymbolTypeAndVectorRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(ClojureParser.RULE_vector);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.SYMBOL);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verify(applier, times(1)).apply(context, token, TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test
    public void testApplyWithSymbolTypeAndParentVectorRule() {

        final RuleContext parentContext = mock(RuleContext.class);
        when(parentContext.getRuleIndex()).thenReturn(ClojureParser.RULE_vector);

        final RuleContext context = mock(RuleContext.class);
        when(context.getParent()).thenReturn(parentContext);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.NUMBER);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verifyZeroInteractions(applier);
    }

    @Test
    public void testApplyWithNumberTypeAndVectorRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(ClojureParser.RULE_vector);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.NUMBER);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verifyZeroInteractions(applier);
    }

    @Test
    public void testApplyWithSymbolTypeAndMapRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(ClojureParser.RULE_map);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.SYMBOL);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verify(applier, times(1)).apply(context, token, TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test
    public void testApplyWithSymbolTypeAndParentMapRule() {

        final RuleContext parentContext = mock(RuleContext.class);
        when(parentContext.getRuleIndex()).thenReturn(ClojureParser.RULE_map);

        final RuleContext context = mock(RuleContext.class);
        when(context.getParent()).thenReturn(parentContext);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.NUMBER);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verifyZeroInteractions(applier);
    }

    @Test
    public void testApplyWithNumberTypeAndMapRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(ClojureParser.RULE_map);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.NUMBER);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verifyZeroInteractions(applier);
    }

    @Test
    public void testApplyWithSymbolTypeAndListRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(ClojureParser.RULE_list);

        final Token token = mock(Token.class);
        when(token.getType()).thenReturn(ClojureParser.SYMBOL);

        new IsCollectionSymbolApplier(applier).apply(context, token, TEST_STRING);

        verifyZeroInteractions(applier);
    }

    @Test
    public void testApplyWithNoType() {

        new IsCollectionSymbolApplier(applier).apply(null, mock(Token.class), TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyWithNullToken() {

        new IsCollectionSymbolApplier(applier).apply(null, null, TEST_STRING);
    }
}
