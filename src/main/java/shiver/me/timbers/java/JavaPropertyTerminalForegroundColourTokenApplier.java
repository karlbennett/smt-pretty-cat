package shiver.me.timbers.java;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.PropertyTerminalForegroundColourTokenApplier;

public class JavaPropertyTerminalForegroundColourTokenApplier extends PropertyTerminalForegroundColourTokenApplier {

    public static final String PREFIX = "java";

    public JavaPropertyTerminalForegroundColourTokenApplier(Class type, FOREGROUND_COLOUR defaultColour) {
        super(PREFIX, type, defaultColour);
    }

    public JavaPropertyTerminalForegroundColourTokenApplier(String name, FOREGROUND_COLOUR defaultColour) {
        super(PREFIX, name, defaultColour);
    }
}
