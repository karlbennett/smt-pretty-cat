package shiver.me.timbers.java;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.antlr4.java.JavaParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This token applier will only apply a transformation against a "@" token.
 */
public class IsAtApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsAtApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isAt(token) ? applier.apply(context, token, string) : string;
    }

    private static boolean isAt(Token token) {

        return JavaParser.AT == token.getType();
    }
}
