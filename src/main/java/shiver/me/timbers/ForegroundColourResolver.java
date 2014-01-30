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
    public FOREGROUND_COLOUR resolve(Class type, FOREGROUND_COLOUR defaultValue) {

        return FOREGROUND_COLOUR.valueOf(resolver.resolve(type, defaultValue.name()));
    }

    @Override
    public FOREGROUND_COLOUR resolve(String name, FOREGROUND_COLOUR defaultValue) {

        return FOREGROUND_COLOUR.valueOf(resolver.resolve(name, defaultValue.name()));
    }
}
