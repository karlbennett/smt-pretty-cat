package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;

/**
 * This wrapped transformer should be used for any unknown file.
 */
public class NullCompositeTokenFileTransformer extends NullCompositeFileTransformer<TokenTransformation> {

    public NullCompositeTokenFileTransformer() {
        super(new IterableTransformations<TokenTransformation>(NULL_TOKEN_TRANSFORMATION));
    }
}
