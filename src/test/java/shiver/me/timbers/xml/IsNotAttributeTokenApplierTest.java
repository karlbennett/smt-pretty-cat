package shiver.me.timbers.xml;

import org.antlr.v4.runtime.RuleContext;
import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.TestUtils.TEST_STRING;
import static shiver.me.timbers.antlr4.xml.XMLParser.RULE_attribute;

public class IsNotAttributeTokenApplierTest {

    private TokenApplier applier;

    @Before
    public void setUp() throws Exception {

        applier = mock(TokenApplier.class);
    }

    @Test
    public void testCreate() {

        new IsNotAttributeTokenApplier(applier);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullColour() {

        new IsNotAttributeTokenApplier(null);
    }

    @Test
    public void testApplyWithNoRule() {

        final RuleContext context = mock(RuleContext.class);

        new IsNotAttributeTokenApplier(applier).apply(context, null, TEST_STRING);

        verify(applier, times(1)).apply(context, null, TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test
    public void testApplyWithAttributeRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(RULE_attribute);

        new IsNotAttributeTokenApplier(applier).apply(context, null, TEST_STRING);

        verifyNoMoreInteractions(applier);
    }

    @Test(expected = NullPointerException.class)
    public void testApplyWithNullRuleContext() {

        new IsNotAttributeTokenApplier(applier).apply(null, null, TEST_STRING);
    }
}
