package shiver.me.timbers.transform;

import shiver.me.timbers.FOREGROUND_COLOUR;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This {@code TerminalForegroundColourTokenApplier} gets it's colour value from a derived system property.
 */
public class PropertyTerminalForegroundColourTokenApplier extends TerminalForegroundColourTokenApplier {

    /**
     * @param prefix        the prefix of the property name e.g. java, xml, json...
     * @param name          the name of the property e.g. Comment, Name, Element...
     * @param defaultColour the colour that the related token should be set to.
     */
    public PropertyTerminalForegroundColourTokenApplier(String prefix, String name, FOREGROUND_COLOUR defaultColour) {
        super(getProperty(prefix, name, defaultColour));
    }

    /**
     * @param prefix        the prefix of the property name e.g. java, xml, json...
     * @param type          the class of the related transformation.
     * @param defaultColour the colour that the related token should be set to.
     */
    public PropertyTerminalForegroundColourTokenApplier(String prefix, Class type, FOREGROUND_COLOUR defaultColour) {
        super(getProperty(prefix, type.getSimpleName(), defaultColour));
    }

    private static FOREGROUND_COLOUR getProperty(String prefix, String name, FOREGROUND_COLOUR defaultColour) {

        assertIsNotNull(argumentIsNullMessage("prefix"), prefix);
        assertIsNotNull(argumentIsNullMessage("name"), name);
        assertIsNotNull(argumentIsNullMessage("defaultColour"), defaultColour);

        return FOREGROUND_COLOUR.valueOf(System.getProperty(prefix + "." + name, defaultColour.name()));
    }
}
