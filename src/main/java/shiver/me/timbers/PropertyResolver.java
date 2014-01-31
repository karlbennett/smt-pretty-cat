package shiver.me.timbers;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNotNull;

/**
 * This resolver can be used to find the property for the supplied prefix and type.
 */
public class PropertyResolver implements ValueResolver<String> {

    public static final String BACKGROUND = "background";
    public static final String FOREGROUND = "foreground";
    public static final String KEYWORDS = "keywords";

    private final String prefix;

    public PropertyResolver() {

        this.prefix = null;
    }

    public PropertyResolver(String prefix) {

        assertIsNotNull(argumentIsNullMessage("prefix"), prefix);

        this.prefix = prefix;
    }

    @Override
    public String resolve(Class type) {

        return getPrefixedProperty(isNotNull(type) ? type.getSimpleName() : null);
    }

    @Override
    public String resolve(String name) {

        return getPrefixedProperty(name);
    }

    private String getPrefixedProperty(String name) {

        return System.getProperty(prependPrefix(name));
    }

    private String prependPrefix(String suffix) {

        return (isNotNull(prefix) ? prefix + "." : "") + suffix;
    }
}
