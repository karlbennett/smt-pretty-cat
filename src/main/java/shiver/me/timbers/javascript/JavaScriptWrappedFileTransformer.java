package shiver.me.timbers.javascript;

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
import shiver.me.timbers.transform.javascript.JavaScriptTransformer;
import shiver.me.timbers.transform.javascript.rules.FormalParameterList;
import shiver.me.timbers.transform.javascript.rules.VariableDeclaration;
import shiver.me.timbers.transform.javascript.types.NumericLiteral;
import shiver.me.timbers.transform.javascript.types.StringLiteral;
import shiver.me.timbers.transform.stream.StringStreamTransformer;

import java.util.Arrays;
import java.util.LinkedList;

import static shiver.me.timbers.PropertyResolver.FOREGROUND;
import static shiver.me.timbers.PropertyResolver.KEYWORDS;
import static shiver.me.timbers.transform.javascript.JavaScriptTransformer.APPLICATION_JAVASCRIPT;
import static shiver.me.timbers.transform.javascript.KeyWords.KEYWORD_NAMES;

public class JavaScriptWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    private static final ValueResolver<FOREGROUND_COLOUR> COLOUR = new ForegroundColourResolver("javascript");

    public JavaScriptWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(APPLICATION_JAVASCRIPT,
                        new StringStreamTransformer<TokenTransformation>(APPLICATION_JAVASCRIPT,
                                new JavaScriptTransformer())),
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
                            new FormalParameterList(
                                    new IsIdentifierApplier(
                                            new TerminalColourApplier(fg, COLOUR.resolve(FormalParameterList.class)))),
                            new NumericLiteral(new TerminalColourApplier(fg, COLOUR.resolve(NumericLiteral.class))),
                            new StringLiteral(new TerminalColourApplier(fg, COLOUR.resolve(StringLiteral.class))),
                            new VariableDeclaration(
                                    new TerminalColourApplier(fg, COLOUR.resolve(VariableDeclaration.class)))
                    ));
                }}
        );
    }
}
