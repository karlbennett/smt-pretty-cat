package shiver.me.timbers;

/**
 * This interface is used to access type configurable, type specific, colour values.
 */
public interface ValueResolver<T> {

    /**
     * Resolve the required value that is related to the supplied type. If no value is found return the
     * {@code defaultValue}.
     *
     * @param type         the class the requires the requested value.
     * @param defaultValue the default value to return if no value is found.
     * @return the requested value if it exists, or the default value.
     */
    public T resolve(Class type, T defaultValue);

    /**
     * Resolve the required value that is has the supplied name. If no value is found return the {@code defaultValue}.
     *
     * @param name         the name of the required value.
     * @param defaultValue the default value to return if no value is found.
     * @return the requested value if it exists, or the default value.
     */
    public T resolve(String name, T defaultValue);
}
