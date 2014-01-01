package shiver.me.timbers.java;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

/**
 * This callable simply instantiates a new {@link JavaWrappedTransformer}.
 */
public class LazyJavaWrappedTransformer extends LazyCompositeTransformer<JavaWrappedTransformer, TokenTransformation> {

    public LazyJavaWrappedTransformer() {
        super(new Callable<JavaWrappedTransformer>() {

            @Override
            public JavaWrappedTransformer call() throws Exception {

                return new JavaWrappedTransformer();
            }
        });
    }
}
