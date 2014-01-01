package shiver.me.timbers.transform;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;

public class TerminalForegroundColourTokenApplierTest {

    @Test
    public void testCreate() {

        new TerminalForegroundColourTokenApplier(BLACK);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNull() {

        new TerminalForegroundColourTokenApplier(null);
    }

    @Test
    public void testApply() {

        final String TEST_STRING = "test_string";

        assertEquals("the string should be transformed correctly.",
                BLACK.escapeSequence() + TEST_STRING + BRIGHT_WHITE.escapeSequence(),
                new TerminalForegroundColourTokenApplier(BLACK).apply(null, null, TEST_STRING));
    }

    @Test
    public void testApplyWithNullString() {

        assertEquals("the string should be transformed correctly.",
                BLACK.escapeSequence() + null + BRIGHT_WHITE.escapeSequence(),
                new TerminalForegroundColourTokenApplier(BLACK).apply(null, null, null));
    }
}
