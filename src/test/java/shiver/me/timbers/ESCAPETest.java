package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.ESCAPE.ESC;
import static shiver.me.timbers.ESCAPE.RESET;

public class ESCAPETest {

    @Test
    public void testToString() {

        assertEquals("the escape character should be correct.", "\033[", ESC.toString());
        assertEquals("the reset sequence should be correct.", "\033[0m", RESET.toString());
    }
}
