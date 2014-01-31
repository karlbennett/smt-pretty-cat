package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.BACKGROUND_COLOUR.BLACK;
import static shiver.me.timbers.BACKGROUND_COLOUR.background;
import static shiver.me.timbers.BACKGROUND_COLOUR.values;
import static shiver.me.timbers.ESCAPE.ESC;

public class BACKGROUND_COLOURTest {

    @Test
    public void testEscapeSequence() {

        for (BACKGROUND_COLOUR colour : values()) {

            assertEquals("colour sequence should be correct.", ESC.toString() + colour.getValue() + "m",
                    colour.escapeSequence());
        }
    }

    @Test
    public void testBackground() {

        assertEquals("the evaluated colour should be correct", BLACK, background("BLACK"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBackgroundWithInvalidName() {

        background("not a colour");
    }
}
