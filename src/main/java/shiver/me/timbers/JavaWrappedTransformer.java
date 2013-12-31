package shiver.me.timbers;

import shiver.me.timbers.transform.IndividualTransformations;
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

import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

public class JavaWrappedTransformer extends WrappedTransformer<TokenTransformation> {

    public JavaWrappedTransformer(ColourConfiguration configuration) {
        super(new JavaTransformer(), configureTransformations(configuration));
    }

    @SuppressWarnings("unchecked")
    private static Transformations<TokenTransformation> configureTransformations(ColourConfiguration configuration) {

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new TerminalForegroundColourTokenApplier(YELLOW));

        return new IndividualTransformations<TokenTransformation>(
                Arrays.<Iterable<TokenTransformation>>asList(
                        keywordTransformations,
                        Arrays.<TokenTransformation>asList(
                                new JavaDoc(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(JavaDoc.class))),
                                new Comment(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(Comment.class))),
                                new LineComment(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(LineComment.class))),
                                new Annotation(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(Annotation.class))),
                                new AnnotationName(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(AnnotationName.class))),
                                new IntegerLiteral(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(IntegerLiteral.class))),
                                new StringLiteral(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(StringLiteral.class))),
                                new VariableDeclaratorId(new TerminalForegroundColourTokenApplier(
                                        configuration.getForegroundColourName(VariableDeclaratorId.class)))
                        )
                ),
                NULL_TOKEN_TRANSFORMATION
        );
    }
}
