package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This transformer supports the transformation of multiple file types.
 */
public class MultiFileTransformer {

    private final Transformers<File, CompositeFileTransformer<TokenTransformation>> transformers;

    public MultiFileTransformer(Transformers<File, CompositeFileTransformer<TokenTransformation>> transformers) {

        assertIsNotNull(argumentIsNullMessage("transformers"), transformers);

        this.transformers = transformers;
    }

    /**
     * Transform the text within the supplied file.
     */
    public String transform(File file) {

        final CompositeFileTransformer<TokenTransformation> transformer = transformers.get(file);

        return transformer.transform(file);
    }
}
