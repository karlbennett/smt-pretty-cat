package shiver.me.timbers;

/**
 * This enum lists all the possible background terminal colours.
 */
public enum BACKGROUND_COLOUR {
    BLACK(40),
    RED(41),
    GREEN(42),
    YELLOW(43),
    BLUE(44),
    MAGENTA(45),
    CYAN(46),
    WHITE(47),
    BRIGHT_BLACK(100),
    BRIGHT_RED(101),
    BRIGHT_GREEN(102),
    BRIGHT_YELLOW(103),
    BRIGHT_BLUE(104),
    BRIGHT_MAGENTA(105),
    BRIGHT_CYAN(106),
    BRIGHT_WHITE(107);

    private final int value;

    private BACKGROUND_COLOUR(int value) {
        this.value = value;
    }

    public int getValue() {

        return value;
    }

    public String escapeSequence() {

        return new EscapeSequence(value).toString();
    }
}
