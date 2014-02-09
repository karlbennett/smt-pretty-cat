package shiver.me.timbers.java;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

import static shiver.me.timbers.transform.java.JavaTransformer.TEXT_X_JAVA_SOURCE;

public class LazyJavaWrappedTransformer
        extends LazyCompositeTransformer<JavaWrappedFileTransformer, TokenTransformation> {

    public LazyJavaWrappedTransformer() {
        super(TEXT_X_JAVA_SOURCE, new Callable<JavaWrappedFileTransformer>() {

            @Override
            public JavaWrappedFileTransformer call() throws Exception {

                return new JavaWrappedFileTransformer();
            }
        });
    }
}
