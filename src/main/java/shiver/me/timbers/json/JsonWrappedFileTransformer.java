package shiver.me.timbers.json;

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

import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_BLUE;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.CYAN;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;
import static shiver.me.timbers.transform.json.JsonTransformer.APPLICATION_JSON;
import static shiver.me.timbers.transform.json.KeyWords.KEYWORD_NAMES;

public class JsonWrappedFileTransformer extends WrappedFileTransformer<TokenTransformation> {

    public JsonWrappedFileTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(APPLICATION_JSON,
                        new StringStreamTransformer<TokenTransformation>(APPLICATION_JSON, new JsonTransformer())),
                configureTransformations()
        );
    }

    @SuppressWarnings("unchecked")
    private static Transformations<TokenTransformation> configureTransformations() {

        final Transformations<TokenTransformation> keywordTransformations = new CompoundTransformations(KEYWORD_NAMES,
                new JsonPropertyTerminalForegroundColourTokenApplier("keywords", YELLOW));

        return new IterableTokenTransformations(
                new LinkedList<TokenTransformation>() {{
                    addAll(keywordTransformations.asCollection());
                    addAll(Arrays.<TokenTransformation>asList(
                            new Member(new IsMemberNameTokenApplier(
                                    new JsonPropertyTerminalForegroundColourTokenApplier(Member.class, CYAN))),
                            new Number(new JsonPropertyTerminalForegroundColourTokenApplier(
                                    Number.class, BRIGHT_BLUE)),
                            new JsonString(new JsonPropertyTerminalForegroundColourTokenApplier(
                                    JsonString.class, BRIGHT_GREEN))
                    ));
                }}
        );
    }
}
