package shiver.me.timbers;

import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNotNull;

/**
 * This resolver can be used to find the property for the supplied prefix and type.
 */
public class PropertyResolver implements ValueResolver<String> {

    private final String prefix;

    public PropertyResolver() {

        this.prefix = null;
    }

    public PropertyResolver(String prefix) {

        assertIsNotNull(argumentIsNullMessage("prefix"), prefix);

        this.prefix = prefix;
    }

    @Override
    public String resolve(Class type, String defaultValue) {

        return getPrefixedProperty(isNotNull(type) ? type.getSimpleName() : null, defaultValue);
    }

    @Override
    public String resolve(String name, String defaultValue) {

        return getPrefixedProperty(name, defaultValue);
    }

    private String prependPrefix(String suffix) {

        return (isNotNull(prefix) ? prefix + "." : "") + suffix;
    }

    private String getPrefixedProperty(String name, String defaultValue) {

        return System.getProperty(prependPrefix(name), defaultValue);
    }
}
