package shiver.me.timbers.java;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.ForegroundColourResolver;
import shiver.me.timbers.ValueResolver;
import shiver.me.timbers.transform.TerminalColourApplier;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.CompoundTransformations;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.WrappedFileTransformer;
import shiver.me.timbers.transform.file.StreamFileTransformer;
import shiver.me.timbers.transform.java.JavaTransformer;
import shiver.me.timbers.transform.java.rules.Annotation;
import shiver.me.timbers.transform.java.rules.AnnotationName;
import shiver.me.timbers.transform.java.rules.VariableDeclaratorId;
import shiver.me.timbers.transform.java.types.Comment;
import shiver.me.timbers.transform.java.types.IntegerLiteral;
import shiver.me.timbers.transform.java.types.JavaDoc;
import shiver.me.timbers.transform.java.types.LineComment;
import shiver.me.timbers.transform.java.types.StringLiteral;
import shiver.me.timbers.transform.stream.StringStreamTransformer;

import java.util.Arrays;
import java.util.LinkedList;

import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.CYAN;
import static shiver.me.timbers.FOREGROUND_COLOUR.GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.RED;
import static shiver.me.timbers.FOREGROUND_COLOUR.WHITE;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.transform.java.JavaTransformer.TEXT_X_JAVA_SOURCE;
import static shiver.me.timbers.transform.java.KeyWords.KEYWORD_NAMES;

public class JavaWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    private static final ValueResolver<FOREGROUND_COLOUR> COLOUR = new ForegroundColourResolver("java");

    public JavaWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(TEXT_X_JAVA_SOURCE,
                        new StringStreamTransformer<TokenTransformation>(TEXT_X_JAVA_SOURCE, new JavaTransformer())),
                configureTransformations()
        );
    }

    @SuppressWarnings("unchecked")
    private static Transformations<TokenTransformation> configureTransformations() {

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new TerminalColourApplier(COLOUR.resolve("keywords", YELLOW)));

        return new IterableTokenTransformations(
                new LinkedList<TokenTransformation>() {{
                    addAll(keywordTransformations.asCollection());
                    addAll(Arrays.<TokenTransformation>asList(
                            new JavaDoc(new TerminalColourApplier(COLOUR.resolve(JavaDoc.class, GREEN))),
                            new Comment(new TerminalColourApplier(COLOUR.resolve(Comment.class, WHITE))),
                            new LineComment(new TerminalColourApplier(COLOUR.resolve(LineComment.class, WHITE))),
                            new Annotation(
                                    new IsAtTokenApplier(
                                            new TerminalColourApplier(COLOUR.resolve(Annotation.class, RED)))),
                            new AnnotationName(new TerminalColourApplier(COLOUR.resolve(AnnotationName.class, RED))),
                            new IntegerLiteral(
                                    new TerminalColourApplier(COLOUR.resolve(IntegerLiteral.class, BRIGHT_BLUE))),
                            new StringLiteral(
                                    new TerminalColourApplier(COLOUR.resolve(StringLiteral.class, BRIGHT_GREEN))),
                            new VariableDeclaratorId(
                                    new TerminalColourApplier(COLOUR.resolve(VariableDeclaratorId.class, CYAN)))
                    ));
                }}
        );
    }
}
