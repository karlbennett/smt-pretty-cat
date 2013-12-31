package shiver.me.timbers;

import shiver.me.timbers.transform.Container;
import shiver.me.timbers.transform.Transformation;
import shiver.me.timbers.transform.WrappedTransformer;

/**
 * An container of {@link shiver.me.timbers.transform.Transformation}s.
 */
public interface Transformers<K, T extends Transformation> extends Container<K, WrappedTransformer<T>> {
}
