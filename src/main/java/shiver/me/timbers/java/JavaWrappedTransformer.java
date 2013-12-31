package shiver.me.timbers.java;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.IndividualTransformations;
import shiver.me.timbers.transform.TerminalForegroundColourTokenApplier;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.WrappedTransformer;
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

import java.util.Arrays;

import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

public class JavaWrappedTransformer extends WrappedTransformer<TokenTransformation> {

    public JavaWrappedTransformer() {
        super(new JavaTransformer(), configureTransformations());
    }

    @SuppressWarnings("unchecked")
    private static Transformations<TokenTransformation> configureTransformations() {

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new TerminalForegroundColourTokenApplier(getProperty("keywords", "YELLOW")));

        return new IndividualTransformations<TokenTransformation>(
                Arrays.<Iterable<TokenTransformation>>asList(
                        keywordTransformations,
                        Arrays.<TokenTransformation>asList(
                                new JavaDoc(new TerminalForegroundColourTokenApplier(
                                        getProperty(JavaDoc.class, "GREEN"))),
                                new Comment(new TerminalForegroundColourTokenApplier(
                                        getProperty(Comment.class, "WHITE"))),
                                new LineComment(new TerminalForegroundColourTokenApplier(
                                        getProperty(LineComment.class, "WHITE"))),
                                new Annotation(new TerminalForegroundColourTokenApplier(
                                        getProperty(Annotation.class, "RED"))),
                                new AnnotationName(new TerminalForegroundColourTokenApplier(
                                        getProperty(AnnotationName.class, "RED"))),
                                new IntegerLiteral(new TerminalForegroundColourTokenApplier(
                                        getProperty(IntegerLiteral.class, "BLUE"))),
                                new StringLiteral(new TerminalForegroundColourTokenApplier(
                                        getProperty(StringLiteral.class, "BRIGHT_GREEN"))),
                                new VariableDeclaratorId(new TerminalForegroundColourTokenApplier(
                                        getProperty(VariableDeclaratorId.class, "CYAN")))
                        )
                ),
                NULL_TOKEN_TRANSFORMATION
        );
    }

    private static FOREGROUND_COLOUR getProperty(Class type, String defaultColour) {

        return getProperty(type.getSimpleName(), defaultColour);
    }

    private static FOREGROUND_COLOUR getProperty(String name, String defaultColour) {

        return FOREGROUND_COLOUR.valueOf(System.getProperty("java." + name, defaultColour));
    }
}
