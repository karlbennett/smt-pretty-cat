package shiver.me.timbers.transform;

import org.apache.commons.io.FilenameUtils;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNotNull;

/**
 * This transformers container allows the lookup of transformers that support the supplied file type.
 */
public class FileTransformers implements Transformers<File, TokenTransformation> {

    private final List<CompositeStreamTransformer<TokenTransformation>> transformers;
    private final Map<String, CompositeStreamTransformer<TokenTransformation>> fileExtensionToTransformers;
    private final CompositeStreamTransformer<TokenTransformation> nullTransformer;

    /**
     * @param fileExtensionToTransformers this map should contain file extensions that map to their related
     *                                    transformers.
     */
    public FileTransformers(Map<String, CompositeStreamTransformer<TokenTransformation>> fileExtensionToTransformers) {

        this(fileExtensionToTransformers, new NullCompositeTransformer());
    }

    /**
     * @param fileExtensionToTransformers this map should contain file extensions that map to their related
     *                                    transformers.
     * @param nullTransformer             this is the transformer that will be run when one cannot be found for the
     *                                    supplied file.
     */
    public FileTransformers(Map<String, CompositeStreamTransformer<TokenTransformation>> fileExtensionToTransformers,
                            CompositeStreamTransformer<TokenTransformation> nullTransformer) {

        assertIsNotNull(argumentIsNullMessage("fileExtensionToTransformers"), fileExtensionToTransformers);
        assertIsNotNull(argumentIsNullMessage("nullTransformer"), nullTransformer);

        this.transformers =
                new ArrayList<CompositeStreamTransformer<TokenTransformation>>(fileExtensionToTransformers.values());
        this.fileExtensionToTransformers = fileExtensionToTransformers;
        this.nullTransformer = nullTransformer;
    }

    @Override
    public CompositeStreamTransformer<TokenTransformation> get(int index) {

        return isValidIndex(index) ? transformers.get(index) : nullTransformer;
    }

    @Override
    public CompositeStreamTransformer<TokenTransformation> get(File key) {

        final CompositeStreamTransformer<TokenTransformation> transformer =
                fileExtensionToTransformers.get(FilenameUtils.getExtension(key.getName()));

        return isNotNull(transformer) ? transformer : nullTransformer;
    }

    @Override
    public Iterator<CompositeStreamTransformer<TokenTransformation>> iterator() {

        return new LinkedList<CompositeStreamTransformer<TokenTransformation>>(transformers).iterator();
    }

    private boolean isValidIndex(int index) {

        return 0 <= index && transformers.size() > index;
    }
}
