package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.ESCAPE.ESC;

public class BACKGROUND_COLOURTest {

    @Test
    public void testEscapeSequence() {

        for (BACKGROUND_COLOUR colour : BACKGROUND_COLOUR.values()) {

            assertEquals("colour sequence should be correct.", ESC.toString() + colour.getValue() + "m",
                    colour.escapeSequence());
        }
    }
}
