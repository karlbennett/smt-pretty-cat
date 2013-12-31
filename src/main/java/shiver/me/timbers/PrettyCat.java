package shiver.me.timbers;

import shiver.me.timbers.transform.IndividualTransformations;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.CompoundTransformations;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;

import static java.util.Arrays.asList;
import static shiver.me.timbers.ESCAPE.RESET;
import static shiver.me.timbers.FOREGROUND_COLOUR.BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.CYAN;
import static shiver.me.timbers.FOREGROUND_COLOUR.GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.RED;
import static shiver.me.timbers.FOREGROUND_COLOUR.WHITE;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

/**
 * This application can be used to print different types of source code to the terminal with syntactic highlighting.
 */
public class PrettyCat {

    private static final Transformations<TokenTransformation> KEYWORD_TRANSFORMATIONS = new CompoundTransformations(
            KEYWORD_NAMES,
            new TerminalForegroundColourTokenApplier(YELLOW)
    );

    @SuppressWarnings("unchecked")
    private static final Transformations<TokenTransformation> TRANSFORMATIONS =
            new IndividualTransformations<TokenTransformation>(
                    asList(
                            KEYWORD_TRANSFORMATIONS,
                            new LinkedList<TokenTransformation>() {{
                                add(new JavaDoc(new TerminalForegroundColourTokenApplier(GREEN)));
                                add(new Comment(new TerminalForegroundColourTokenApplier(WHITE)));
                                add(new LineComment(new TerminalForegroundColourTokenApplier(WHITE)));
                                add(new Annotation(new TerminalForegroundColourTokenApplier(RED)));
                                add(new AnnotationName(new TerminalForegroundColourTokenApplier(RED)));
                                add(new IntegerLiteral(new TerminalForegroundColourTokenApplier(BLUE)));
                                add(new StringLiteral(new TerminalForegroundColourTokenApplier(BRIGHT_GREEN)));
                                add(new VariableDeclaratorId(new TerminalForegroundColourTokenApplier(CYAN)));
                            }}
                    ),
                    NULL_TOKEN_TRANSFORMATION
            );

    public static void main(String[] args) throws FileNotFoundException {

        final InputStream stream = new FileInputStream(new File(args[0]));

        // Reset the colour scheme after printing the highlighted source code.
        System.out.println(new JavaTransformer().transform(stream, TRANSFORMATIONS) + RESET);
    }
}
