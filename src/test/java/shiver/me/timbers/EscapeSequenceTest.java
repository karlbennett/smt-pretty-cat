package shiver.me.timbers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EscapeSequenceTest {

    private static final int TEST_ESCAPE_VALUE_ONE = 999;
    private static final int TEST_ESCAPE_VALUE_TWO = 1000;

    private static final String TEST_ESCAPE_SEQUENCE_ONE = "\033[" + TEST_ESCAPE_VALUE_ONE + "m";
    private static final String TEST_ESCAPE_SEQUENCE_TWO = "\033[" + TEST_ESCAPE_VALUE_TWO + "m";

    @Test
    public void testCreate() {

        new EscapeSequence(0);
    }

    @Test
    public void testLength() {

        assertEquals("the length of the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_ONE.length(),
                new EscapeSequence(TEST_ESCAPE_VALUE_ONE).length());

        assertEquals("the length of the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_TWO.length(),
                new EscapeSequence(TEST_ESCAPE_VALUE_TWO).length());
    }

    @Test
    public void testCharAt() {

        assertEquals("the chars of the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_ONE.charAt(4),
                new EscapeSequence(TEST_ESCAPE_VALUE_ONE).charAt(4));

        assertEquals("the chars on the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_TWO.charAt(5),
                new EscapeSequence(TEST_ESCAPE_VALUE_TWO).charAt(5));
    }

    @Test
    public void testSubSequence() {

        assertEquals("the sub sequences of the escape sequence should be correct.",
                TEST_ESCAPE_SEQUENCE_ONE.subSequence(2, 4),
                new EscapeSequence(TEST_ESCAPE_VALUE_ONE).subSequence(2, 4));

        assertEquals("the sub sequences on the escape sequence should be correct.",
                TEST_ESCAPE_SEQUENCE_TWO.subSequence(3, 5),
                new EscapeSequence(TEST_ESCAPE_VALUE_TWO).subSequence(3, 5));
    }

    @Test
    public void testToString() {

        assertEquals("the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_ONE,
                new EscapeSequence(TEST_ESCAPE_VALUE_ONE).toString());

        assertEquals("the escape sequence should be correct.", TEST_ESCAPE_SEQUENCE_TWO,
                new EscapeSequence(TEST_ESCAPE_VALUE_TWO).toString());
    }
}
