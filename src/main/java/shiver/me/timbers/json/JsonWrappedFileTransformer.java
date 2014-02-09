package shiver.me.timbers.json;

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
import shiver.me.timbers.transform.json.JsonTransformer;
import shiver.me.timbers.transform.json.rules.Member;
import shiver.me.timbers.transform.json.types.JsonString;
import shiver.me.timbers.transform.json.types.Number;
import shiver.me.timbers.transform.stream.StringStreamTransformer;

import java.util.Arrays;
import java.util.LinkedList;

import static shiver.me.timbers.PropertyResolver.FOREGROUND;
import static shiver.me.timbers.PropertyResolver.KEYWORDS;
import static shiver.me.timbers.transform.json.JsonTransformer.APPLICATION_JSON;
import static shiver.me.timbers.transform.json.KeyWords.KEYWORD_NAMES;

public class JsonWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    private static final ValueResolver<FOREGROUND_COLOUR> COLOUR = new ForegroundColourResolver("json");

    public JsonWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(APPLICATION_JSON,
                        new StringStreamTransformer<TokenTransformation>(APPLICATION_JSON, new JsonTransformer())),
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
                            new Member(new IsMemberNameApplier(
                                    new TerminalColourApplier(fg, COLOUR.resolve(Member.class)))),
                            new Number(new TerminalColourApplier(fg, COLOUR.resolve(Number.class))),
                            new JsonString(new TerminalColourApplier(fg, COLOUR.resolve(JsonString.class)))
                    ));
                }}
        );
    }
}
