package shiver.me.timbers.json;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.antlr4.json.JsonParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This token applier will only apply a transformation against a member name token.
 */
public class IsMemberNameTokenApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsMemberNameTokenApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isMemberName(token) ? applier.apply(context, token, string) : string;
    }

    private static boolean isMemberName(Token token) {

        return JsonParser.T__3 != token.getType();
    }
}
