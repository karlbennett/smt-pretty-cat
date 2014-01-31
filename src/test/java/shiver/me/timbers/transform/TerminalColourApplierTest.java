package shiver.me.timbers.transform;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;

public class TerminalColourApplierTest {

    @Test
    public void testCreate() {

        new TerminalColourApplier(BRIGHT_WHITE, BLACK);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullForeground() {

        new TerminalColourApplier(null, BLACK);
    }

    @Test(expected = AssertionError.class)
    public void testCreateWithNullColour() {

        new TerminalColourApplier(BRIGHT_WHITE, null);
    }

    @Test
    public void testApply() {

        final String TEST_STRING = "test_string";

        assertEquals("the string should be transformed correctly.",
                BLACK.escapeSequence() + TEST_STRING + BRIGHT_WHITE.escapeSequence(),
                new TerminalColourApplier(BRIGHT_WHITE, BLACK).apply(null, null, TEST_STRING));
    }

    @Test
    public void testApplyWithNullString() {

        assertEquals("the string should be transformed correctly.",
                BLACK.escapeSequence() + null + BRIGHT_WHITE.escapeSequence(),
                new TerminalColourApplier(BRIGHT_WHITE, BLACK).apply(null, null, null));
    }
}
