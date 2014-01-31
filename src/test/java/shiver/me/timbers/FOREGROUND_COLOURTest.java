package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.ESCAPE.ESC;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.foreground;
import static shiver.me.timbers.FOREGROUND_COLOUR.values;

public class FOREGROUND_COLOURTest {

    @Test
    public void testEscapeSequence() {

        for (FOREGROUND_COLOUR colour : values()) {

            assertEquals("colour sequence should be correct.", ESC.toString() + colour.getValue() + "m",
                    colour.escapeSequence());
        }
    }

    @Test
    public void testForeground() {

        assertEquals("the evaluated colour should be correct", BLACK, foreground("BLACK"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForegroundWithInvalidName() {

        foreground("not a colour");
    }
}
