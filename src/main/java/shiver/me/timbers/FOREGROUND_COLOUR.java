package shiver.me.timbers;

/**
 * This enum lists all the possible foreground terminal colours.
 */
public enum FOREGROUND_COLOUR {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),
    BRIGHT_BLACK(90),
    BRIGHT_RED(91),
    BRIGHT_GREEN(92),
    BRIGHT_YELLOW(93),
    BRIGHT_BLUE(94),
    BRIGHT_MAGENTA(95),
    BRIGHT_CYAN(96),
    BRIGHT_WHITE(97);

    private final int value;

    private FOREGROUND_COLOUR(int value) {
        this.value = value;
    }

    public int getValue() {

        return value;
    }

    public String escapeSequence() {

        return new EscapeSequence(value).toString();
    }
}
