package shiver.me.timbers.transform;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.FileUtils.testTxtContents;
import static shiver.me.timbers.FileUtils.testTxtFile;

public class NullTransformerTest {

    @Test
    public void testTransform() {

        @SuppressWarnings("unchecked")
        final Transformations<TokenTransformation> transformations = mock(Transformations.class);
        when(transformations.get(anyString())).thenReturn(new TestTokenTransformation());

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullTransformer().transform(testTxtFile(), transformations));
    }

    @Test(expected = RuntimeException.class)
    @SuppressWarnings("unchecked")
    public void testTransformWithInvalidFile() throws IOException {

        new NullTransformer().transform(new File("no file here."), mock(Transformations.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testTransformWithNullInputStream() {

        assertNull("the null value should be passed through.", new NullTransformer().transform(null, mock(Transformations.class)));
    }

    @Test
    public void testTransformWithNullTransformations() {

        assertEquals("the text should not be transformed.", testTxtContents(),
                new NullTransformer().transform(testTxtFile(), null));
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
