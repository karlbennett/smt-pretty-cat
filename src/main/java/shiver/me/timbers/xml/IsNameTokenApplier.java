package shiver.me.timbers.xml;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.antlr4.xml.XMLParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This token applier will only apply a transformation against a "Name" token.
 */
public class IsNameTokenApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsNameTokenApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isName(token) ? applier.apply(context, token, string) : string;
    }

    private static boolean isName(Token token) {

        return XMLParser.Name == token.getType();
    }
}
