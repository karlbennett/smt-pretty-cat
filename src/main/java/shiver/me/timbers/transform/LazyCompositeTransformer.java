package shiver.me.timbers.transform;

import java.io.InputStream;
import java.util.concurrent.Callable;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNull;

/**
 * This {@link CompositeTransformer} will lazily create the it's related transformer just before calling either
 * transform method.
 */
public class LazyCompositeTransformer<L extends CompositeTransformer<T>, T extends Transformation>
        implements CompositeTransformer<T> {

    private final Callable<L> callable;
    private L transformer;

    public LazyCompositeTransformer(Callable<L> callable) {

        assertIsNotNull(argumentIsNullMessage("callable"), callable);

        this.callable = callable;
    }

    @Override
    public String transform(InputStream stream) {

        return transformer().transform(stream);
    }

    @Override
    public String transform(InputStream stream, Transformations<T> transformations) {

        return transformer().transform(stream, transformations);
    }

    private CompositeTransformer<T> transformer() {

        if (isNull(transformer)) {

            return setAndRetrieveTransformer();
        }

        return transformer;
    }

    private CompositeTransformer<T> setAndRetrieveTransformer() {

        try {

            return transformer = callable.call();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
