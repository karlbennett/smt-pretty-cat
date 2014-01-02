package shiver.me.timbers.transform;

import org.apache.commons.io.IOUtils;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.IOException;
import java.io.InputStream;

/**
 * This transformer should be used for any unknown file.
 */
public class NullTransformer implements StreamTransformer<TokenTransformation> {

    @Override
    public String transform(InputStream stream, Transformations<TokenTransformation> transformations) {

        try {

            return IOUtils.toString(stream);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
