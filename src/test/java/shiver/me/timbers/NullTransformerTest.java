package shiver.me.timbers;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.Test;
import shiver.me.timbers.transform.Transformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.FileUtils.testInputStream;
import static shiver.me.timbers.FileUtils.testText;

public class NullTransformerTest {

    @Test
    public void testTransform() {

        @SuppressWarnings("unchecked")
        final Transformations<TokenTransformation> transformations = mock(Transformations.class);
        when(transformations.get(anyString())).thenReturn(new TestTokenTransformation());

        assertEquals("the text should not be transformed.", testText(),
                new NullTransformer().transform(testInputStream(), transformations));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithClosedInputStream() throws IOException {

        final InputStream stream = testInputStream();
        stream.close();

        new NullTransformer().transform(stream, mock(Transformations.class));
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        new NullTransformer().transform(null, mock(Transformations.class));
    }

    @Test
    public void testTransformWithNullTransformations() {

        assertEquals("the text should not be transformed.", testText(),
                new NullTransformer().transform(testInputStream(), null));
    }


    private static class TestTokenTransformation implements TokenTransformation {

        @Override
        public String apply(RuleContext context, Token token, String string) {

            return "string transformed.";
        }

        @Override
        public String getName() {

            return null;
        }
    }
}
