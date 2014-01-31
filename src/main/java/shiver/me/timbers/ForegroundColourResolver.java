package shiver.me.timbers;

/**
 * This resolver can be used to find the foreground colour for the supplied type.
 */
public class ForegroundColourResolver implements ValueResolver<FOREGROUND_COLOUR> {

    private final PropertyResolver resolver;

    public ForegroundColourResolver() {

        resolver = new PropertyResolver();
    }

    public ForegroundColourResolver(String prefix) {

        resolver = new PropertyResolver(prefix);
    }

    @Override
    public FOREGROUND_COLOUR resolve(Class type) {

        return FOREGROUND_COLOUR.valueOf(resolver.resolve(type));
    }

    @Override
    public FOREGROUND_COLOUR resolve(String name) {

        return FOREGROUND_COLOUR.valueOf(resolver.resolve(name));
    }
}
