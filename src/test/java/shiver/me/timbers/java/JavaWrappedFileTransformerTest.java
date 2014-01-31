package shiver.me.timbers.java;

import org.junit.Test;

import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class JavaWrappedFileTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() {

        new JavaWrappedFileTransformer();
    }
}
