package shiver.me.timbers.configuration;

import java.io.File;

/**
 * This class can be used to find the pretty cat configuration directory.
 */
public class ConfigDir {

    public static final String DIR_NAME = ".pcat";

    public static String homePath() {

        return System.getProperty("user.home");
    }

    public static String path() {

        return homePath() + "/" + DIR_NAME;
    }

    public static File createIfNecessary(String path) {

        final File dir = new File(path);

        if (dir.exists() && dir.isDirectory()) {

            return dir;
        }

        return create(dir);
    }

    public static File create(File dir) {

        if (dir.mkdir()) {

            return dir;
        }

        throw new IllegalStateException("could not create directory: " + dir.getPath());
    }
}
