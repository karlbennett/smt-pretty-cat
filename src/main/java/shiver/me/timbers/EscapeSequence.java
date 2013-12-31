package shiver.me.timbers;

import static shiver.me.timbers.ESCAPE.ESC;

/**
 * This class will produce a terminal colour escape sequence for the supplied number.
 */
public class EscapeSequence implements CharSequence {

    private final String escapeSequence;

    public EscapeSequence(int value) {

        this.escapeSequence = ESC.toString() + value + 'm';
    }

    @Override
    public int length() {

        return escapeSequence.length();
    }

    @Override
    public char charAt(int index) {

        return escapeSequence.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {

        return escapeSequence.subSequence(start, end);
    }

    @Override
    public String toString() {

        return escapeSequence;
    }
}
