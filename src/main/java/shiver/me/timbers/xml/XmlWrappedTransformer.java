package shiver.me.timbers.xml;

import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.ForegroundColourResolver;
import shiver.me.timbers.ValueResolver;
import shiver.me.timbers.transform.TerminalColourApplier;
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
import static shiver.me.timbers.transform.xml.XmlTransformer.TEXT_XML;

public class XmlWrappedTransformer extends WrappedFileTransformer<TokenTransformation> {

    private static final ValueResolver<FOREGROUND_COLOUR> COLOUR = new ForegroundColourResolver("xml");

    public XmlWrappedTransformer() {
        super(
                new StreamFileTransformer<TokenTransformation>(TEXT_XML,
                        new StringStreamTransformer<TokenTransformation>(TEXT_XML, new XmlTransformer())),
                configureTransformations()
        );
    }

    private static Transformations<TokenTransformation> configureTransformations() {

        return new IterableTokenTransformations(
                Arrays.<TokenTransformation>asList(
                        new XMLDeclOpen(new TerminalColourApplier(COLOUR.resolve(XMLDeclOpen.class, BRIGHT_YELLOW))),
                        new SpecialClose(new TerminalColourApplier(COLOUR.resolve(SpecialClose.class, BRIGHT_YELLOW))),
                        new Name(new IsNotAttributeTokenApplier(
                                new TerminalColourApplier(COLOUR.resolve(Name.class, YELLOW)))),
                        new Attribute(new IsNameTokenApplier(
                                new TerminalColourApplier(COLOUR.resolve(Attribute.class, CYAN)))),
                        new Open(new TerminalColourApplier(COLOUR.resolve(Open.class, BRIGHT_YELLOW))),
                        new Close(new TerminalColourApplier(COLOUR.resolve(Close.class, BRIGHT_YELLOW))),
                        new Slash(new TerminalColourApplier(COLOUR.resolve(Slash.class, BRIGHT_YELLOW))),
                        new XMLString(new TerminalColourApplier(COLOUR.resolve(XMLString.class, BRIGHT_GREEN)))
                )
        );
    }
}
