package shiver.me.timbers.transform;

import org.apache.commons.io.FilenameUtils;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;
import shiver.me.timbers.transform.mapped.MappedTransformers;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * This transformers container allows the lookup of transformers that support the supplied file type.
 */
public class CompositeFileTransformers implements Transformers<File, CompositeFileTransformer<TokenTransformation>> {

    private final Transformers<String, CompositeFileTransformer<TokenTransformation>> transformers;

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

        transformers = new MappedTransformers<String, CompositeFileTransformer<TokenTransformation>>(
                fileExtensionToTransformers, nullTransformer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeFileTransformer<TokenTransformation> get(int index) {

        return transformers.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeFileTransformer<TokenTransformation> get(File key) {

        return transformers.get(FilenameUtils.getExtension(key.getName()));
    }

    @Override
    public Iterator<CompositeFileTransformer<TokenTransformation>> iterator() {

        return transformers.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<CompositeFileTransformer<TokenTransformation>> asCollection() {

        return transformers.asCollection();
    }
}
