package shiver.me.timbers.xml;

import org.antlr.v4.runtime.RuleContext;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;
import static shiver.me.timbers.antlr4.xml.XMLParser.RULE_attribute;

public class NamePropertyTerminalForegroundColourTokenApplierTest {

    @Test
    public void testCreate() {

        new NamePropertyTerminalForegroundColourTokenApplier(BLACK);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullColour() {

        new NamePropertyTerminalForegroundColourTokenApplier(null);
    }

    @Test
    public void testApply() {

        final String TEST_STRING = "test_string";

        assertEquals("the string should be transformed correctly.",
                BLACK.escapeSequence() + TEST_STRING + BRIGHT_WHITE.escapeSequence(),
                new NamePropertyTerminalForegroundColourTokenApplier(BLACK)
                        .apply(mock(RuleContext.class), null, TEST_STRING));
    }

    @Test
    public void testApplyWithAttributeRule() {

        final RuleContext context = mock(RuleContext.class);
        when(context.getRuleIndex()).thenReturn(RULE_attribute);

        final String TEST_STRING = "test_string";

        assertEquals("the string should be transformed correctly.", TEST_STRING,
                new NamePropertyTerminalForegroundColourTokenApplier(BLACK).apply(context, null, TEST_STRING));
    }
}
