package shiver.me.timbers.transform;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.antlr4.TokenApplier;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This transformer will add terminal foreground colour escape sequences to the supplied string.
 */
public class TerminalColourApplier implements TokenApplier {

    private final FOREGROUND_COLOUR foreground;
    private final FOREGROUND_COLOUR colour;

    public TerminalColourApplier(FOREGROUND_COLOUR foreground, FOREGROUND_COLOUR colour) {

        assertIsNotNull(argumentIsNullMessage("foreground"), foreground);
        assertIsNotNull(argumentIsNullMessage("colour"), colour);

        this.foreground = foreground;
        this.colour = colour;
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return colour.escapeSequence() + string + foreground.escapeSequence();
    }
}
