package shiver.me.timbers.xml;

import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.WrappedFileTransformer;
import shiver.me.timbers.transform.file.StreamFileTransformer;
import shiver.me.timbers.transform.stream.StringStreamTransformer;
import shiver.me.timbers.transform.xml.XmlTransformer;
import shiver.me.timbers.transform.xml.rules.Attribute;
import shiver.me.timbers.transform.xml.types.Close;
import shiver.me.timbers.transform.xml.types.Name;
import shiver.me.timbers.transform.xml.types.Open;
import shiver.me.timbers.transform.xml.types.Slash;
import shiver.me.timbers.transform.xml.types.SpecialClose;
import shiver.me.timbers.transform.xml.types.XMLDeclOpen;
import shiver.me.timbers.transform.xml.types.XMLString;

import java.util.Arrays;

import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_GREEN;
import static shiver.me.timbers.FOREGROUND_COLOUR.BRIGHT_YELLOW;
import static shiver.me.timbers.FOREGROUND_COLOUR.CYAN;
import static shiver.me.timbers.FOREGROUND_COLOUR.YELLOW;

public class XmlWrappedTransformer extends WrappedFileTransformer<TokenTransformation> {

    public XmlWrappedTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(
                        new StringStreamTransformer<TokenTransformation>(new XmlTransformer())),
                configureTransformations()
        );
    }

    private static Transformations<TokenTransformation> configureTransformations() {

        return new IterableTokenTransformations(
                Arrays.<TokenTransformation>asList(
                        new XMLDeclOpen(
                                new XmlPropertyTerminalForegroundColourTokenApplier(XMLDeclOpen.class, BRIGHT_YELLOW)),
                        new SpecialClose(
                                new XmlPropertyTerminalForegroundColourTokenApplier(SpecialClose.class, BRIGHT_YELLOW)),
                        new Name(new IsNotAttributeTokenApplier(
                                new XmlPropertyTerminalForegroundColourTokenApplier(SpecialClose.class, YELLOW))),
                        new Attribute(new IsNameTokenApplier(
                                new XmlPropertyTerminalForegroundColourTokenApplier(Attribute.class, CYAN))),
                        new Open(new XmlPropertyTerminalForegroundColourTokenApplier(Open.class, BRIGHT_YELLOW)),
                        new Close(new XmlPropertyTerminalForegroundColourTokenApplier(Close.class, BRIGHT_YELLOW)),
                        new Slash(new XmlPropertyTerminalForegroundColourTokenApplier(Slash.class, BRIGHT_YELLOW)),
                        new XMLString(
                                new XmlPropertyTerminalForegroundColourTokenApplier(XMLString.class, BRIGHT_GREEN))
                )
        );
    }
}
