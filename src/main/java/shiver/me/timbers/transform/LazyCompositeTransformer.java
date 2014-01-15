package shiver.me.timbers.transform;

import shiver.me.timbers.transform.composite.CompositeFileTransformer;

import javax.activation.MimeType;
import java.io.File;
import java.util.concurrent.Callable;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNull;

/**
 * This {@link shiver.me.timbers.transform.composite.CompositeStreamTransformer} will lazily create the it's related
 * transformer just before calling either transform method.
 */
public class LazyCompositeTransformer<L extends CompositeFileTransformer<T>, T extends Transformation>
        extends AbstractTransformer<File, T> implements CompositeFileTransformer<T> {

    private final Callable<L> callable;
    private L transformer;

    public LazyCompositeTransformer(MimeType mimeType, Callable<L> callable) {
        super(mimeType);

        assertIsNotNull(argumentIsNullMessage("callable"), callable);

        this.callable = callable;
    }

    @Override
    public String transform(File file) {

        return transformer().transform(file);
    }

    @Override
    public String transform(File file, Transformations<T> transformations) {

        return transformer().transform(file, transformations);
    }

    private CompositeFileTransformer<T> transformer() {

        if (isNull(transformer)) {

            return setAndRetrieveTransformer();
        }

        return transformer;
    }

    private CompositeFileTransformer<T> setAndRetrieveTransformer() {

        try {

            return transformer = callable.call();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
