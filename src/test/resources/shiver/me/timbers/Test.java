package shiver.me.timbers;

import java.util.concurrent.Callable;

/**
 * This class is to be used for an acceptance test of the {@link PrettyCat} class.
 */
public abstract class Test implements Callable<Integer> {

    @SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
    private final String test;

    public Test() {
        // A comment.
        this.test = "a string";
    }

    protected abstract void test();

    @Override
    public Integer call() throws Exception {

        return 42;
    }
}
