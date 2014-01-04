package shiver.me.timbers.transform;

import org.apache.commons.io.FilenameUtils;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
public class CompositeFileTransformers implements Transformers<File, CompositeFileTransformer<TokenTransformation>> {

    private final List<CompositeFileTransformer<TokenTransformation>> transformers;
    private final Map<String, CompositeFileTransformer<TokenTransformation>> fileExtensionToTransformers;
    private final CompositeFileTransformer<TokenTransformation> nullTransformer;

    /**
     * @param fileExtensionToTransformers this map should contain file extensions that map to their related
     *                                    transformers.
     */
    public CompositeFileTransformers(Map<String, CompositeFileTransformer<TokenTransformation>> fileExtensionToTransformers) {

        this(fileExtensionToTransformers, new NullCompositeTokenFileTransformer());
    }

    /**
     * @param fileExtensionToTransformers this map should contain file extensions that map to their related
     *                                    transformers.
     * @param nullTransformer             this is the transformer that will be run when one cannot be found for the
     *                                    supplied file.
     */
    public CompositeFileTransformers(Map<String, CompositeFileTransformer<TokenTransformation>> fileExtensionToTransformers,
                                     CompositeFileTransformer<TokenTransformation> nullTransformer) {

        assertIsNotNull(argumentIsNullMessage("fileExtensionToTransformers"), fileExtensionToTransformers);
        assertIsNotNull(argumentIsNullMessage("nullTransformer"), nullTransformer);

        this.transformers =
                new ArrayList<CompositeFileTransformer<TokenTransformation>>(fileExtensionToTransformers.values());
        this.fileExtensionToTransformers = fileExtensionToTransformers;
        this.nullTransformer = nullTransformer;
    }

    @Override
    public CompositeFileTransformer<TokenTransformation> get(int index) {

        return isValidIndex(index) ? transformers.get(index) : nullTransformer;
    }

    @Override
    public CompositeFileTransformer<TokenTransformation> get(File key) {

        final CompositeFileTransformer<TokenTransformation> transformer =
                fileExtensionToTransformers.get(FilenameUtils.getExtension(key.getName()));

        return isNotNull(transformer) ? transformer : nullTransformer;
    }

    @Override
    public Collection<CompositeFileTransformer<TokenTransformation>> asCollection() {

        return new ArrayList<CompositeFileTransformer<TokenTransformation>>(transformers);
    }

    @Override
    public Iterator<CompositeFileTransformer<TokenTransformation>> iterator() {

        return new LinkedList<CompositeFileTransformer<TokenTransformation>>(transformers).iterator();
    }

    private boolean isValidIndex(int index) {

        return 0 <= index && transformers.size() > index;
    }
}
