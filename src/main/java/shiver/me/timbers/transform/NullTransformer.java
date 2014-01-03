package shiver.me.timbers.transform;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

/**
 * This transformer should be used for any unknown file.
 */
public class NullTransformer extends StreamFileTransformer<TokenTransformation> {

    public NullTransformer() {
        super(new StringStreamTransformer<TokenTransformation>(new StringTransformer<TokenTransformation>() {

            @Override
            public String transform(String input, Transformations<TokenTransformation> transformations) {

                return input;
            }
        }));
    }
}
