package shiver.me.timbers.xml;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.antlr4.xml.XMLParser.RULE_attribute;
import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This token applier will stop the application of transformations against tokens that have a parent rule of
 * "attribute".
 */
public class IsNotAttributeTokenApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsNotAttributeTokenApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isNotAttribute(context) ? applier.apply(context, token, string) : string;
    }

    private static boolean isNotAttribute(RuleContext context) {

        return RULE_attribute != context.getRuleIndex();
    }
}
