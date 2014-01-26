package shiver.me.timbers.json;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.PropertyTerminalForegroundColourTokenApplier;

public class JsonPropertyTerminalForegroundColourTokenApplier extends PropertyTerminalForegroundColourTokenApplier {

    public static final String PREFIX = "json";

    public JsonPropertyTerminalForegroundColourTokenApplier(Class type, FOREGROUND_COLOUR defaultColour) {
        super(PREFIX, type, defaultColour);
    }

    public JsonPropertyTerminalForegroundColourTokenApplier(String name, FOREGROUND_COLOUR defaultColour) {
        super(PREFIX, name, defaultColour);
    }
}
