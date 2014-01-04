package shiver.me.timbers.transform;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class CompositeFileTransformersTest {

    private static final String TEST_EXTENSION = "ext";
    @SuppressWarnings("unchecked")
    private static final CompositeFileTransformer<TokenTransformation> TEST_NULL_TRANSFORMER = mock(WrappedFileTransformer.class);

    private CompositeFileTransformer<TokenTransformation> transformer;
    private Map<String, CompositeFileTransformer<TokenTransformation>> transformerMap;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {

        transformer = mock(CompositeFileTransformer.class);
        transformerMap = transformerMap(
                new SimpleEntry<String, CompositeFileTransformer<TokenTransformation>>(TEST_EXTENSION, transformer));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateWithMap() {

        new CompositeFileTransformers(mock(Map.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullMap() {

        new CompositeFileTransformers(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateWithMapAndTransformer() {

        new CompositeFileTransformers(mock(Map.class), mock(WrappedFileTransformer.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullMapAndTransformer() {

        new CompositeFileTransformers(null, mock(WrappedFileTransformer.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithMapAndNullTransformer() {

        new CompositeFileTransformers(mock(Map.class), null);
    }

    @Test
    public void testGetWithIndex() {

        assertEquals("the correct transformer should be returned.", transformer, new CompositeFileTransformers(transformerMap,
                TEST_NULL_TRANSFORMER).get(0));
    }

    @Test
    public void testGetWithIndexInvalid() {

        assertEquals("the null transformer should be returned.", TEST_NULL_TRANSFORMER,
                new CompositeFileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(-1));
        assertEquals("the null transformer should be returned.", TEST_NULL_TRANSFORMER,
                new CompositeFileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(transformerMap.size()));
    }

    @Test
    public void testGetWithFile() {

        final File file = new File("test." + TEST_EXTENSION);

        assertEquals("the correct transformer should be returned.", transformer, new CompositeFileTransformers(transformerMap,
                TEST_NULL_TRANSFORMER).get(file));
    }

    @Test
    public void testGetWithFileInvalidFile() {

        final File file = new File("test.inv");

        assertEquals("the correct transformer should be returned.", TEST_NULL_TRANSFORMER,
                new CompositeFileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(file));
    }

    @Test
    public void testIterator() {

        assertCollection(transformerMap.values(), new CompositeFileTransformers(transformerMap, TEST_NULL_TRANSFORMER));
    }

    @Test
    public void testAsCollection() {

        assertCollection(transformerMap.values(),
                new CompositeFileTransformers(transformerMap, TEST_NULL_TRANSFORMER).asCollection());
    }

    private static Map<String, CompositeFileTransformer<TokenTransformation>> transformerMap(
            Entry<String, CompositeFileTransformer<TokenTransformation>>... entries) {

        final Map<String, CompositeFileTransformer<TokenTransformation>> transformerMap =
                new HashMap<String, CompositeFileTransformer<TokenTransformation>>(entries.length);

        for (Entry<String, CompositeFileTransformer<TokenTransformation>> entry : entries) {

            transformerMap.put(entry.getKey(), entry.getValue());
        }

        return transformerMap;
    }

    private void assertCollection(Collection<CompositeFileTransformer<TokenTransformation>> expected,
                                  Iterable<CompositeFileTransformer<TokenTransformation>> actual) {

        int i = 0;
        for (CompositeFileTransformer<TokenTransformation> transformer : actual) {

            assertThat("the transformer should be contained in the supplied map.", expected,
                    hasItem(transformer));
            i++;
        }

        assertEquals("the correct number if iterations should have occurred.", expected.size(), i);
    }
}
