package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

import static shiver.me.timbers.transform.antlr4.NullTokenTransformation.NULL_TOKEN_TRANSFORMATION;

/**
 * This wrapped transformer should be used for any unknown file.
 */
public class NullWrappedTransformer extends WrappedTransformer<TokenTransformation> {

    public NullWrappedTransformer() {
        super(new NullTransformer(), new IndividualTransformations<TokenTransformation>(NULL_TOKEN_TRANSFORMATION));
    }
}
