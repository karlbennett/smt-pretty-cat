package shiver.me.timbers;

import shiver.me.timbers.exceptions.ExceptionHandler;
import shiver.me.timbers.exceptions.RethrowingExceptionHandler;
import shiver.me.timbers.java.LazyJavaWrappedTransformer;
import shiver.me.timbers.transform.Container;
import shiver.me.timbers.transform.MultiFileTransformer;
import shiver.me.timbers.transform.Transformers;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;
import shiver.me.timbers.transform.iterable.IterableTransformers;
import shiver.me.timbers.transform.mapped.MappedContainer;
import shiver.me.timbers.xml.LazyXmlWrappedTransformer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static java.lang.System.out;
import static shiver.me.timbers.ESCAPE.RESET;
import static shiver.me.timbers.exceptions.Exceptions.withExceptionHandling;
import static shiver.me.timbers.transform.NullCompositeTokenFileTransformer.NULL_COMPOSITE_TOKEN_FILE_TRANSFORMER;

/**
 * This application can be used to print different types of source code to the terminal with syntactic highlighting.
 */
public class PrettyCat {

    private static final Transformers<CompositeFileTransformer<TokenTransformation>> TRANSFORMERS =
            new IterableTransformers<CompositeFileTransformer<TokenTransformation>>(
                    new ArrayList<CompositeFileTransformer<TokenTransformation>>() {{
                        add(new LazyJavaWrappedTransformer());
                        add(new LazyXmlWrappedTransformer());
                    }},
                    NULL_COMPOSITE_TOKEN_FILE_TRANSFORMER
            );

    private static final Container<Class, ExceptionHandler> EXCEPTION_HANDLERS =
            new MappedContainer<Class, ExceptionHandler>(new RethrowingExceptionHandler());

    public static void main(final String[] args) throws Throwable {

        withExceptionHandling(EXCEPTION_HANDLERS, new Callable<Void>() {

            @Override
            public Void call() throws Exception {

                out.print(new MultiFileTransformer(TRANSFORMERS).transform(new File(args[0])));
                // Reset the colour scheme after printing the highlighted source code.
                out.println(RESET);

                return null;
            }
        });
    }
}
