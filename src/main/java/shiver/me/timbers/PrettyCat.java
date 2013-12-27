package shiver.me.timbers;

import shiver.me.timbers.rules.Annotation;
import shiver.me.timbers.rules.AnnotationName;
import shiver.me.timbers.rules.VariableDeclaratorId;
import shiver.me.timbers.transform.Applyer;
import shiver.me.timbers.transform.CompoundTransformations;
import shiver.me.timbers.transform.IndividualTransformations;
import shiver.me.timbers.transform.Transformation;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.types.Comment;
import shiver.me.timbers.types.IntegerLiteral;
import shiver.me.timbers.types.JavaDoc;
import shiver.me.timbers.types.LineComment;
import shiver.me.timbers.types.StringLiteral;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static shiver.me.timbers.KeyWords.KEYWORD_NAMES;

/**
 * This application can be used to print different types of source code to the terminal with syntactic highlighting.
 */
public class PrettyCat {

    private static final String ESC = "\033[";
    private static final String RESET = "\033[0m";

    private static final int RED = 31;
    private static final int GREEN = 32;
    private static final int YELLOW = 33;
    private static final int BLUE = 34;
    private static final int CYAN = 36;
    private static final int WHITE = 37;
    private static final int BRIGHT_GREEN = 92;
    private static final int BRIGHT_WHITE = 97;

    private static final Transformations KEYWORD_TRANSFORMATIONS = new CompoundTransformations(
            KEYWORD_NAMES,
            new SimpleTerminalColourApplyer(YELLOW)
    );

    @SuppressWarnings("unchecked")
    private static final Transformations TRANSFORMATIONS = new IndividualTransformations(
            asList(
                    KEYWORD_TRANSFORMATIONS,
                    new LinkedList<Transformation>() {{
                        add(new JavaDoc(new SimpleTerminalColourApplyer(GREEN)));
                        add(new Comment(new SimpleTerminalColourApplyer(WHITE)));
                        add(new LineComment(new SimpleTerminalColourApplyer(WHITE)));
                        add(new Annotation(new SimpleTerminalColourApplyer(RED)));
                        add(new AnnotationName(new SimpleTerminalColourApplyer(RED)));
                        add(new IntegerLiteral(new SimpleTerminalColourApplyer(BLUE)));
                        add(new StringLiteral(new SimpleTerminalColourApplyer(BRIGHT_GREEN)));
                        add(new VariableDeclaratorId(new SimpleTerminalColourApplyer(CYAN)));
                    }}
            )
    );

    public static void main(String[] args) throws IOException {

        final InputStream stream = new FileInputStream(new File(args[0]));

        // Reset the colour scheme after printing the highlighted source code.
        System.out.println(new JavaTransformer().transform(stream, TRANSFORMATIONS) + RESET);
    }

    private static class SimpleTerminalColourApplyer implements Applyer {

        private final int colour;

        private SimpleTerminalColourApplyer(int colour) {
            this.colour = colour;
        }

        @Override
        public String apply(String string) {

            return ESC + colour + "m" + string + ESC + BRIGHT_WHITE + "m";
        }
    }
}
