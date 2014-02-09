package shiver.me.timbers.json;

import shiver.me.timbers.transform.LazyCompositeTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.util.concurrent.Callable;

import static shiver.me.timbers.transform.json.JsonTransformer.APPLICATION_JSON;

public class LazyJsonWrappedTransformer
        extends LazyCompositeTransformer<JsonWrappedFileTransformer, TokenTransformation> {

    public LazyJsonWrappedTransformer() {
        super(APPLICATION_JSON, new Callable<JsonWrappedFileTransformer>() {

            @Override
            public JsonWrappedFileTransformer call() throws Exception {

                return new JsonWrappedFileTransformer();
            }
        });
    }
}
