package shiver.me.timbers.transform;

import org.apache.commons.io.FilenameUtils;
import shiver.me.timbers.BACKGROUND_COLOUR;
import shiver.me.timbers.FOREGROUND_COLOUR;
import shiver.me.timbers.transform.antlr4.TokenTransformation;
import shiver.me.timbers.transform.composite.CompositeFileTransformer;

import javax.activation.MimeType;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.transform.java.JavaTransformer.TEXT_X_JAVA_SOURCE;
import static shiver.me.timbers.transform.javascript.JavaScriptTransformer.APPLICATION_JAVASCRIPT;
import static shiver.me.timbers.transform.json.JsonTransformer.APPLICATION_JSON;
import static shiver.me.timbers.transform.xml.XmlTransformer.TEXT_XML;

/**
 * This transformer supports the transformation of multiple file types.
 */
public class MultiFileTransformer {

    private static final Map<String, MimeType> FILE_EXTENSION_TO_MIME_TYPE = new HashMap<String, MimeType>() {{
        put("java", TEXT_X_JAVA_SOURCE);
        put("js", APPLICATION_JAVASCRIPT);
        put("xml", TEXT_XML);
        put("json", APPLICATION_JSON);
    }};

    private final Transformers<CompositeFileTransformer<TokenTransformation>> transformers;
    private final BACKGROUND_COLOUR background;
    private final FOREGROUND_COLOUR foreground;

    public MultiFileTransformer(Transformers<CompositeFileTransformer<TokenTransformation>> transformers,
                                BACKGROUND_COLOUR background, FOREGROUND_COLOUR foreground) {

        assertIsNotNull(argumentIsNullMessage("transformers"), transformers);
        assertIsNotNull(argumentIsNullMessage("background"), background);
        assertIsNotNull(argumentIsNullMessage("foreground"), foreground);

        this.transformers = transformers;
        this.background = background;
        this.foreground = foreground;
    }

    /**
     * Transform the text within the supplied file.
     */
    public String transform(File file) {

        final MimeType mimeType = FILE_EXTENSION_TO_MIME_TYPE.get(FilenameUtils.getExtension(file.getName()));

        final CompositeFileTransformer<TokenTransformation> transformer = transformers.get(mimeType);

        return background.escapeSequence() + foreground.escapeSequence() + transformer.transform(file);
    }
}
