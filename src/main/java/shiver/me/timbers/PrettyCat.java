package shiver.me.timbers;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.transform.IndividualTransformations;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.CompoundTransformations;
import shiver.me.timbers.transform.antlr4.TokenApplier;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.java.JavaTransformer;
import shiver.me.timbers.transform.java.rules.Annotation;
import shiver.me.timbers.transform.java.rules.AnnotationName;
import shiver.me.timbers.transform.java.rules.VariableDeclaratorId;
import shiver.me.timbers.transform.java.types.Comment;
import shiver.me.timbers.transform.java.types.IntegerLiteral;
import shiver.me.timbers.transform.java.types.JavaDoc;
import shiver.me.timbers.transform.java.types.LineComment;
import shiver.me.timbers.transform.java.types.StringLiteral;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

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

    private static final Transformations<TokenTransformation> KEYWORD_TRANSFORMATIONS = new CompoundTransformations(
            KEYWORD_NAMES,
            new SimpleTerminalColourApplier(YELLOW)
    );

    @SuppressWarnings("unchecked")
    private static final Transformations<TokenTransformation> TRANSFORMATIONS =
            new IndividualTransformations<TokenTransformation>(
                    asList(
                            KEYWORD_TRANSFORMATIONS,
                            new LinkedList<TokenTransformation>() {{
                                add(new JavaDoc(new SimpleTerminalColourApplier(GREEN)));
                                add(new Comment(new SimpleTerminalColourApplier(WHITE)));
                                add(new LineComment(new SimpleTerminalColourApplier(WHITE)));
                                add(new Annotation(new SimpleTerminalColourApplier(RED)));
                                add(new AnnotationName(new SimpleTerminalColourApplier(RED)));
                                add(new IntegerLiteral(new SimpleTerminalColourApplier(BLUE)));
                                add(new StringLiteral(new SimpleTerminalColourApplier(BRIGHT_GREEN)));
                                add(new VariableDeclaratorId(new SimpleTerminalColourApplier(CYAN)));
                            }}
                    ),
                    NULL_TOKEN_TRANSFORMATION
            );

    public static void main(String[] args) throws IOException {

        final InputStream stream = new FileInputStream(new File(args[0]));

        // Reset the colour scheme after printing the highlighted source code.
        System.out.println(new JavaTransformer().transform(stream, TRANSFORMATIONS) + RESET);
    }

    private static class SimpleTerminalColourApplier implements TokenApplier {

        private final int colour;

        private SimpleTerminalColourApplier(int colour) {
            this.colour = colour;
        }

        @Override
        public String apply(RuleContext context, Token token, String string) {

            return ESC + colour + "m" + string + ESC + BRIGHT_WHITE + "m";
        }
    }
}
