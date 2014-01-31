package shiver.me.timbers.json;

import org.junit.Test;

import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class JsonWrappedFileTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() {

        new JsonWrappedFileTransformer();
    }
}
