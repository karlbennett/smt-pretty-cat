package shiver.me.timbers.clojure;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.antlr4.clojure.ClojureParser;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNull;

/**
 * This token applier will only apply a transformation against a {@code Symbol} that falls with a {@code Vector} rule.
 */
public class IsCollectionSymbolApplier implements TokenApplier {

    private final TokenApplier applier;

    public IsCollectionSymbolApplier(TokenApplier applier) {

        assertIsNotNull(argumentIsNullMessage("applier"), applier);

        this.applier = applier;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isSymbol(token) && isCollection(context) ? applier.apply(context, token, string) : string;
    }

    private static boolean isSymbol(Token token) {

        return ClojureParser.SYMBOL == token.getType();
    }

    private static boolean isCollection(RuleContext context) {

        if (isNull(context)) {

            return false;
        }

        if (isVector(context) || isMap(context)) {

            return true;
        }

        return isCollection(context.getParent());
    }

    private static boolean isVector(RuleContext context) {

        return ClojureParser.RULE_vector == context.getRuleIndex();
    }

    private static boolean isMap(RuleContext context) {

        return ClojureParser.RULE_map == context.getRuleIndex();
    }
}