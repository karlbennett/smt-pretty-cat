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

import static shiver.me.timbers.PropertyResolver.FOREGROUND;
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

        final FOREGROUND_COLOUR fg = new ForegroundColourResolver().resolve(FOREGROUND);

        return new IterableTokenTransformations(
                Arrays.<TokenTransformation>asList(
                        new XMLDeclOpen(new TerminalColourApplier(fg, COLOUR.resolve(XMLDeclOpen.class))),
                        new SpecialClose(new TerminalColourApplier(fg, COLOUR.resolve(SpecialClose.class))),
                        new Name(new IsNotAttributeTokenApplier(
                                new TerminalColourApplier(fg, COLOUR.resolve(Name.class)))),
                        new Attribute(new IsNameTokenApplier(
                                new TerminalColourApplier(fg, COLOUR.resolve(Attribute.class)))),
                        new Open(new TerminalColourApplier(fg, COLOUR.resolve(Open.class))),
                        new Close(new TerminalColourApplier(fg, COLOUR.resolve(Close.class))),
                        new Slash(new TerminalColourApplier(fg, COLOUR.resolve(Slash.class))),
                        new XMLString(new TerminalColourApplier(fg, COLOUR.resolve(XMLString.class)))
                )
        );
    }
}
