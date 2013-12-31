package shiver.me.timbers;

import shiver.me.timbers.transform.antlr4.TokenTransformation;

/**
 * This interface should be implemented with the configuration for the for each languages colour
 * transformations.
 */
public interface ColourConfiguration {

    /**
     * Given the type of transformation, return the name of the foreground colour that it should be set to.
     */
    public <T extends TokenTransformation> FOREGROUND_COLOUR getForegroundColourName(Class<T> type);
}
