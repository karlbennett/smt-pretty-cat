package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.ESCAPE.ESC;

public class FOREGROUND_COLOURTest {

    @Test
    public void testEscapeSequence() {

        for (FOREGROUND_COLOUR colour : FOREGROUND_COLOUR.values()) {

            assertEquals("colour sequence should be correct.", ESC.toString() + colour.getValue() + "m",
                    colour.escapeSequence());

            // the following is just to get 100% coverage.
            FOREGROUND_COLOUR.valueOf(colour.name());
        }
    }
}
