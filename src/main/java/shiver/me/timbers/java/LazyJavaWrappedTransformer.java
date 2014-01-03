package shiver.me.timbers.java;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

/**
 * This callable simply instantiates a new {@link JavaWrappedFileTransformer}.
 */
public class LazyJavaWrappedTransformer extends LazyCompositeTransformer<JavaWrappedFileTransformer, TokenTransformation> {

    public LazyJavaWrappedTransformer() {
        super(new Callable<JavaWrappedFileTransformer>() {

            @Override
            public JavaWrappedFileTransformer call() throws Exception {

                return new JavaWrappedFileTransformer();
            }
        });
    }
}
