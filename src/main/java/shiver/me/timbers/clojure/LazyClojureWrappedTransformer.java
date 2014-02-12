package shiver.me.timbers.clojure;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

import static shiver.me.timbers.transform.clojure.ClojureTransformer.TEXT_X_CLOJURE;

public class LazyClojureWrappedTransformer
        extends LazyCompositeTransformer<ClojureWrappedFileTransformer, TokenTransformation> {

    public LazyClojureWrappedTransformer() {
        super(TEXT_X_CLOJURE, new Callable<ClojureWrappedFileTransformer>() {

            @Override
            public ClojureWrappedFileTransformer call() throws Exception {

                return new ClojureWrappedFileTransformer();
            }
        });
    }
}
