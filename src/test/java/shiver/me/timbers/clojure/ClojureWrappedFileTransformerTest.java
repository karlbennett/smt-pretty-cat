package shiver.me.timbers.clojure;

import org.junit.Test;

import static shiver.me.timbers.TestUtils.CONFIGURATION;

public class ClojureWrappedFileTransformerTest {

    static {
        @SuppressWarnings("UnusedDeclaration")
        final Object o = CONFIGURATION;
    }

    @Test
    public void testCreate() {

        new ClojureWrappedFileTransformer();
    }
}
