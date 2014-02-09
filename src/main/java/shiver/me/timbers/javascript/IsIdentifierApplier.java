package shiver.me.timbers.javascript;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.antlr4.JavaScriptParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This token applier will only apply a transformation against an identifier token.
 */
public class IsIdentifierApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsIdentifierApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isIdentifier(token) ? applier.apply(context, token, string) : string;
    }

    private static boolean isIdentifier(Token token) {

        return JavaScriptParser.Identifier == token.getType();
    }
}
