package shiver.me.timbers.xml;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

import static shiver.me.timbers.transform.xml.XmlTransformer.TEXT_XML;

public class LazyXmlWrappedTransformer extends LazyCompositeTransformer<XmlWrappedTransformer, TokenTransformation> {

    public LazyXmlWrappedTransformer() {
        super(TEXT_XML, new Callable<XmlWrappedTransformer>() {

            @Override
            public XmlWrappedTransformer call() throws Exception {

                return new XmlWrappedTransformer();
            }
        });
    }
}
