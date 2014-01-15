package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.IterableTokenTransformations;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.NullCompositeFileTransformer;

/**
 * This wrapped transformer should be used for any unknown file.
 */
public class NullCompositeTokenFileTransformer extends NullCompositeFileTransformer<TokenTransformation> {

    public static final NullCompositeTokenFileTransformer NULL_COMPOSITE_TOKEN_FILE_TRANSFORMER =
            new NullCompositeTokenFileTransformer();

    public NullCompositeTokenFileTransformer() {
        super(new IterableTokenTransformations());
    }
}
