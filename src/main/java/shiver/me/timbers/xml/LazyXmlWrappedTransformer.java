package shiver.me.timbers.xml;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

/**
 * This callable simply instantiates a new {@link XmlWrappedTransformer}.
 */
public class LazyXmlWrappedTransformer extends LazyCompositeTransformer<XmlWrappedTransformer, TokenTransformation> {

    public LazyXmlWrappedTransformer() {
        super(new Callable<XmlWrappedTransformer>() {

            @Override
            public XmlWrappedTransformer call() throws Exception {

                return new XmlWrappedTransformer();
            }
        });
    }
}
