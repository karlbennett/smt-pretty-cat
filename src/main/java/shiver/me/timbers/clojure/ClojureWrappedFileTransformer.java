package shiver.me.timbers.clojure;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.ForegroundColourResolver;
import shiver.me.timbers.ValueResolver;
import shiver.me.timbers.transform.TerminalColourApplier;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.CompoundTransformations;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.clojure.ClojureTransformer;
import shiver.me.timbers.transform.clojure.rules.Literal;
import shiver.me.timbers.transform.clojure.types.ClojureString;
import shiver.me.timbers.transform.clojure.types.Comment;
import shiver.me.timbers.transform.clojure.types.Keyword;
import shiver.me.timbers.transform.clojure.types.Number;
import shiver.me.timbers.transform.clojure.types.Symbol;
import shiver.me.timbers.transform.composite.WrappedFileTransformer;
import shiver.me.timbers.transform.file.StreamFileTransformer;
import shiver.me.timbers.transform.stream.StringStreamTransformer;

import java.util.Arrays;
import java.util.LinkedList;

import static shiver.me.timbers.PropertyResolver.FOREGROUND;
import static shiver.me.timbers.PropertyResolver.KEYWORDS;
import static shiver.me.timbers.transform.clojure.ClojureTransformer.TEXT_X_CLOJURE;
import static shiver.me.timbers.transform.clojure.KeyWords.KEYWORD_NAMES;

public class ClojureWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    private static final ValueResolver<FOREGROUND_COLOUR> COLOUR = new ForegroundColourResolver("clojure");

    public ClojureWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(TEXT_X_CLOJURE,
                        new StringStreamTransformer<TokenTransformation>(TEXT_X_CLOJURE, new ClojureTransformer())),
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
                            new Symbol(new TerminalColourApplier(fg, COLOUR.resolve(Symbol.class))),
                            new Comment(new TerminalColourApplier(fg, COLOUR.resolve(Comment.class))),
                            new ClojureString(new TerminalColourApplier(fg, COLOUR.resolve(ClojureString.class))),
                            new Number(new TerminalColourApplier(fg, COLOUR.resolve(Number.class))),
                            new Keyword(new TerminalColourApplier(fg, COLOUR.resolve(Keyword.class))),
                            new Literal(
                                    new IsCollectionSymbolApplier(
                                            new TerminalColourApplier(fg, COLOUR.resolve("Collection"))))
                    ));
                }}
        );
    }
}
