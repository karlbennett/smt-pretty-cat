package shiver.me.timbers.transform;

/**
 * A container of {@link shiver.me.timbers.transform.Transformation}s.
 */
public interface Transformers<K, T extends Transformation> extends Container<K, CompositeTransformer<T>> {
}
