package shiver.me.timbers.xml;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.PropertyTerminalForegroundColourTokenApplier;

public class XmlPropertyTerminalForegroundColourTokenApplier extends PropertyTerminalForegroundColourTokenApplier {

    public static final String PREFIX = "xml";

    public XmlPropertyTerminalForegroundColourTokenApplier(Class type, FOREGROUND_COLOUR defaultColour) {
        super(PREFIX, type, defaultColour);
    }
}
