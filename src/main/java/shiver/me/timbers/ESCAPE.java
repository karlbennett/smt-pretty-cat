package shiver.me.timbers;

/**
 * This enum contains the terminal escape sequences.
 */
public enum ESCAPE {

    ESC("\033["),
    RESET(ESC + "0m");

    private final String value;

    private ESCAPE(String value) {

        this.value = value;
    }

    @Override
    public String toString() {

        return value;
    }
}
