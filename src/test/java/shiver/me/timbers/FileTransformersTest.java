package shiver.me.timbers;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.transform.WrappedTransformer;
import shiver.me.timbers.transform.antlr4.TokenTransformation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class FileTransformersTest {

    private static final String TEST_EXTENSION = "ext";
    @SuppressWarnings("unchecked")
    private static final WrappedTransformer<TokenTransformation> TEST_NULL_TRANSFORMER = mock(WrappedTransformer.class);

    private WrappedTransformer<TokenTransformation> transformer;
    private Map<String, WrappedTransformer<TokenTransformation>> transformerMap;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {

        transformer = mock(WrappedTransformer.class);
        transformerMap = transformerMap(
                new SimpleEntry<String, WrappedTransformer<TokenTransformation>>(TEST_EXTENSION, transformer));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateWithMap() {

        new FileTransformers(mock(Map.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullMap() {

        new FileTransformers(null);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateWithMapAndTransformer() {

        new FileTransformers(mock(Map.class), mock(WrappedTransformer.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithNullMapAndTransformer() {

        new FileTransformers(null, mock(WrappedTransformer.class));
    }

    @Test(expected = AssertionError.class)
    @SuppressWarnings("unchecked")
    public void testCreateWithMapAndNullTransformer() {

        new FileTransformers(mock(Map.class), null);
    }

    @Test
    public void testGetWithIndex() {

        assertEquals("the correct transformer should be returned.", transformer, new FileTransformers(transformerMap,
                TEST_NULL_TRANSFORMER).get(0));
    }

    @Test
    public void testGetWithIndexInvalid() {

        assertEquals("the null transformer should be returned.", TEST_NULL_TRANSFORMER,
                new FileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(-1));
        assertEquals("the null transformer should be returned.", TEST_NULL_TRANSFORMER,
                new FileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(transformerMap.size()));
    }

    @Test
    public void testGetWithFile() {

        final File file = new File("test." + TEST_EXTENSION);

        assertEquals("the correct transformer should be returned.", transformer, new FileTransformers(transformerMap,
                TEST_NULL_TRANSFORMER).get(file));
    }

    @Test
    public void testGetWithFileInvalidFile() {

        final File file = new File("test.inv");

        assertEquals("the correct transformer should be returned.", TEST_NULL_TRANSFORMER,
                new FileTransformers(transformerMap, TEST_NULL_TRANSFORMER).get(file));
    }

    @Test
    public void testIterator() {

        int i = 0;
        for (WrappedTransformer<TokenTransformation> transformer :
                new FileTransformers(transformerMap, TEST_NULL_TRANSFORMER)) {

            assertThat("the transformer should be contained in the supplied map.", transformerMap.values(),
                    hasItem(transformer));
            i++;
        }

        assertEquals("the correct number if iterations should have occured.", transformerMap.size(), i);
    }

    private static Map<String, WrappedTransformer<TokenTransformation>> transformerMap(
            Entry<String, WrappedTransformer<TokenTransformation>>... entries) {

        final Map<String, WrappedTransformer<TokenTransformation>> transformerMap =
                new HashMap<String, WrappedTransformer<TokenTransformation>>(entries.length);

        for (Entry<String, WrappedTransformer<TokenTransformation>> entry : entries) {

            transformerMap.put(entry.getKey(), entry.getValue());
        }

        return transformerMap;
    }
}
