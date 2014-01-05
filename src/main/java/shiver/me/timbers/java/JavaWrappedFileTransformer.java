package shiver.me.timbers.java;

import shiver.me.timbers.transform.IterableTransformations;
import shiver.me.timbers.transform.StreamFileTransformer;
import shiver.me.timbers.transform.StringStreamTransformer;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.WrappedFileTransformer;
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
import java.util.LinkedList;

import static shiver.me.timbers.FOREGROUND_COLOUR.BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.CYAN;
import static shiver.me.timbers.FOREGROUND_COLOUR.GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.RED;
import static shiver.me.timbers.FOREGROUND_COLOUR.WHITE;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

public class JavaWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    public JavaWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(
                        new StringStreamTransformer<TokenTransformation>(new JavaTransformer())),
                configureTransformations()
        );
    }

    @SuppressWarnings("unchecked")
    private static Transformations<TokenTransformation> configureTransformations() {

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new JavaPropertyTerminalForegroundColourTokenApplier("keywords", YELLOW));

        return new IterableTransformations<TokenTransformation>(
                new LinkedList<TokenTransformation>() {{
                    addAll(keywordTransformations.asCollection());
                    addAll(Arrays.<TokenTransformation>asList(
                            new JavaDoc(new JavaPropertyTerminalForegroundColourTokenApplier(JavaDoc.class, GREEN)),
                            new Comment(new JavaPropertyTerminalForegroundColourTokenApplier(Comment.class, WHITE)),
                            new LineComment(new JavaPropertyTerminalForegroundColourTokenApplier(LineComment.class,
                                    WHITE)),
                            new Annotation(new JavaPropertyTerminalForegroundColourTokenApplier(Annotation.class,
                                    RED)),
                            new AnnotationName(new JavaPropertyTerminalForegroundColourTokenApplier(
                                    AnnotationName.class, RED)),
                            new IntegerLiteral(new JavaPropertyTerminalForegroundColourTokenApplier(
                                    IntegerLiteral.class, BLUE)),
                            new StringLiteral(new JavaPropertyTerminalForegroundColourTokenApplier(
                                    StringLiteral.class, BRIGHT_GREEN)),
                            new VariableDeclaratorId(new JavaPropertyTerminalForegroundColourTokenApplier(
                                    VariableDeclaratorId.class, CYAN))
                    ));
                }},
                NULL_TOKEN_TRANSFORMATION
        );
    }
}
