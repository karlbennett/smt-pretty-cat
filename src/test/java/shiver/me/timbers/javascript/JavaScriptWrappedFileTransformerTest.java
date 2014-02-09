package shiver.me.timbers.javascript;

import org.junit.Test;

import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class JavaScriptWrappedFileTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() {

        new JavaScriptWrappedFileTransformer();
    }
}
