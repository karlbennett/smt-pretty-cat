package shiver.me.timbers;

import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.java.rules.Annotation;
import shiver.me.timbers.transform.java.rules.AnnotationName;
import shiver.me.timbers.transform.java.rules.VariableDeclaratorId;
import shiver.me.timbers.transform.java.types.Comment;
import shiver.me.timbers.transform.java.types.IntegerLiteral;
import shiver.me.timbers.transform.java.types.JavaDoc;
import shiver.me.timbers.transform.java.types.LineComment;
import shiver.me.timbers.transform.java.types.StringLiteral;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.getProperty;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_WHITE;
import static shiver.me.timbers.FOREGROUND_COLOUR.valueOf;
import static shiver.me.timbers.checks.Checks.isNotNull;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORDS;

public class JavaColourConfiguration implements ColourConfiguration {

    private final Map<Class, String> colourMap = buildColourMap();

    @Override
    public <T extends TokenTransformation> FOREGROUND_COLOUR getForegroundColourName(Class<T> type) {

        final String colourName = colourMap.get(type);

        return isNotNull(colourName) ? valueOf(colourName) : BRIGHT_WHITE;
    }

    private static Map<Class, String> buildColourMap() {

        final Map<Class, String> colourMap = new HashMap<Class, String>();

        putProperties(colourMap, KEYWORDS, "YELLOW");
        putProperty(colourMap, JavaDoc.class, "GREEN");
        putProperty(colourMap, Comment.class, "WHITE");
        putProperty(colourMap, LineComment.class, "WHITE");
        putProperty(colourMap, Annotation.class, "RED");
        putProperty(colourMap, AnnotationName.class, "RED");
        putProperty(colourMap, IntegerLiteral.class, "BLUE");
        putProperty(colourMap, StringLiteral.class, "BRIGHT_GREEN");
        putProperty(colourMap, VariableDeclaratorId.class, "CYAN");

        return colourMap;
    }

    private static void putProperties(Map<Class, String> colourMap, Iterable<Class> types, String defaultColour) {

        for (Class type : types) {
            putProperty(colourMap, type, defaultColour);
        }
    }

    private static void putProperty(Map<Class, String> colourMap, Class type, String defaultColour) {

        colourMap.put(type, getProperty("java." + type.getSimpleName(), defaultColour));
    }
}
