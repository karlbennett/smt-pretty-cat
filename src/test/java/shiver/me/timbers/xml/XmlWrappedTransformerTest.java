package shiver.me.timbers.xml;

import org.junit.Test;

import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class XmlWrappedTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() {

        new XmlWrappedTransformer();
    }
}
