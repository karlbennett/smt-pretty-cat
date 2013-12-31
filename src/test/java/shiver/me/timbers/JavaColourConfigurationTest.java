package shiver.me.timbers;

import org.junit.Test;
import shiver.me.timbers.transform.java.rules.Annotation;
import shiver.me.timbers.transform.java.types.Comment;
import shiver.me.timbers.transform.java.types.IntegerLiteral;
import shiver.me.timbers.transform.java.types.JavaDoc;
import shiver.me.timbers.transform.java.types.StringLiteral;

import java.util.Collection;
import java.util.HashSet;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.junit.Assert.assertEquals;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_BLACK;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;
import static shiver.me.timbers.FOREGROUND_COLOUR.GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.RED;
import static shiver.me.timbers.FOREGROUND_COLOUR.WHITE;
import static shiver.me.timbers.checks.Checks.isNotNull;

public class JavaColourConfigurationTest {

    private Collection<Entry<String, String>> properties;

    @Test
    public void testGetForegroundColourName() {

        final ColourConfiguration configuration = new JavaColourConfiguration();

        assertEquals("\"JavaDoc\" colour should be correct.", GREEN,
                configuration.getForegroundColourName(JavaDoc.class));
        assertEquals("\"Comment\" colour should be correct.", WHITE,
                configuration.getForegroundColourName(Comment.class));
        assertEquals("\"Annotation\" colour should be correct.", RED,
                configuration.getForegroundColourName(Annotation.class));
        assertEquals("\"IntegerLiteral\" colour should be correct.", BLUE,
                configuration.getForegroundColourName(IntegerLiteral.class));
    }

    @Test
    public void testGetForegroundColourNameWithSetSystemProperties() {

        final String annotationPropertyName = "java." + Annotation.class.getSimpleName();
        final String stringLiteralPropertyName = "java." + StringLiteral.class.getSimpleName();

        recordProperties(annotationPropertyName, stringLiteralPropertyName);

        System.setProperty(annotationPropertyName, "BRIGHT_BLACK");
        System.setProperty(stringLiteralPropertyName, "BRIGHT_WHITE");

        final ColourConfiguration configuration = new JavaColourConfiguration();

        assertEquals("\"Annotation\" colour should be correct.", BRIGHT_BLACK,
                configuration.getForegroundColourName(Annotation.class));
        assertEquals("\"StringLiteral\" colour should be correct.", BRIGHT_WHITE,
                configuration.getForegroundColourName(StringLiteral.class));

        restoreProperties();
    }

    private void recordProperties(String... names) {

        properties = new HashSet<Entry<String, String>>(names.length);

        for (String name : names) {

            properties.add(new SimpleEntry<String, String>(name, System.getProperty(name)));
        }
    }

    private void restoreProperties() {

        for (Entry<String, String> property : properties) {

            if (isNotNull(property.getValue())) {

                System.setProperty(property.getKey(), property.getValue());

            } else {

                System.clearProperty(property.getKey());
            }
        }
    }
}
