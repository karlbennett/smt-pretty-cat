package shiver.me.timbers.xml;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.xml.types.Name;

import static shiver.me.timbers.antlr4.xml.XMLParser.RULE_attribute;

/**
 * This token applier is specifically for an XML {@link Name} token, it checks to make sure the given token isn't an
 * attribute before marking it as an XML name.
 */
public class NamePropertyTerminalForegroundColourTokenApplier extends XmlPropertyTerminalForegroundColourTokenApplier {

    public NamePropertyTerminalForegroundColourTokenApplier(FOREGROUND_COLOUR defaultColour) {
        super(Name.class, defaultColour);
    }

    @Override
    public String apply(RuleContext context, Token token, String string) {

        return isNotAttribute(context) ? super.apply(context, token, string) : string;
    }

    private static boolean isNotAttribute(RuleContext context) {

        return RULE_attribute != context.getRuleIndex();
    }
}
