package shiver.me.timbers.javascript;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

import static shiver.me.timbers.transform.javascript.JavaScriptTransformer.APPLICATION_JAVASCRIPT;

public class LazyJavaScriptWrappedTransformer
        extends LazyCompositeTransformer<JavaScriptWrappedFileTransformer, TokenTransformation> {

    public LazyJavaScriptWrappedTransformer() {
        super(APPLICATION_JAVASCRIPT, new Callable<JavaScriptWrappedFileTransformer>() {

            @Override
            public JavaScriptWrappedFileTransformer call() throws Exception {

                return new JavaScriptWrappedFileTransformer();
            }
        });
    }
}
