package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;

/**
 * This transformer supports the transformation of multiple file types.
 */
public class FileTransformer {

    private final Transformers<File, TokenTransformation> transformers;

    public FileTransformer(Transformers<File, TokenTransformation> transformers) {

        assertIsNotNull(argumentIsNullMessage("transformers"), transformers);

        this.transformers = transformers;
    }

    /**
     * Transform the text within the supplied file.
     */
    public String transform(File file) {

        final WrappedTransformer<TokenTransformation> transformer = transformers.get(file);

        try {

            final InputStream stream = new FileInputStream(file);

            return transformer.transform(stream);

        } catch (FileNotFoundException e) {

            throw new RuntimeException(e);
        }

    }
}
