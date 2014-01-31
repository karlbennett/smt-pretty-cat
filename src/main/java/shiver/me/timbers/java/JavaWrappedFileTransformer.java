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

import static shiver.me.timbers.PropertyResolver.FOREGROUND;
import static shiver.me.timbers.PropertyResolver.KEYWORDS;
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

        final FOREGROUND_COLOUR fg = new ForegroundColourResolver().resolve(FOREGROUND);

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new TerminalColourApplier(fg, COLOUR.resolve(KEYWORDS)));

        return new IterableTokenTransformations(
                new LinkedList<TokenTransformation>() {{
                    addAll(keywordTransformations.asCollection());
                    addAll(Arrays.<TokenTransformation>asList(
                            new JavaDoc(new TerminalColourApplier(fg, COLOUR.resolve(JavaDoc.class))),
                            new Comment(new TerminalColourApplier(fg, COLOUR.resolve(Comment.class))),
                            new LineComment(new TerminalColourApplier(fg, COLOUR.resolve(LineComment.class))),
                            new Annotation(
                                    new IsAtTokenApplier(
                                            new TerminalColourApplier(fg, COLOUR.resolve(Annotation.class)))),
                            new AnnotationName(new TerminalColourApplier(fg, COLOUR.resolve(AnnotationName.class))),
                            new IntegerLiteral(new TerminalColourApplier(fg, COLOUR.resolve(IntegerLiteral.class))),
                            new StringLiteral(new TerminalColourApplier(fg, COLOUR.resolve(StringLiteral.class))),
                            new VariableDeclaratorId(
                                    new TerminalColourApplier(fg, COLOUR.resolve(VariableDeclaratorId.class)))
                    ));
                }}
        );
    }
}
