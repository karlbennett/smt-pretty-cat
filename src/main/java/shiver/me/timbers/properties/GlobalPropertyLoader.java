package shiver.me.timbers.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.util.Map.Entry;
import static shiver.me.timbers.asserts.Asserts.argumentIsNullMessage;
import static shiver.me.timbers.asserts.Asserts.assertIsNotNull;
import static shiver.me.timbers.checks.Checks.isNull;

/**
 * This class can be used to load properties into the global scope.
 */
public class GlobalPropertyLoader {

    private final InputStream stream;

    public GlobalPropertyLoader(InputStream stream) {

        assertIsNotNull(argumentIsNullMessage("stream"), stream);

        this.stream = stream;
    }

    public void load() {

        final Properties properties = new Properties();

        try {

            properties.load(stream);

            for (Entry entry : properties.entrySet()) {

                if (isNull(System.getProperty(entry.getKey().toString()))) {

                    System.setProperty(entry.getKey().toString(), entry.getValue().toString());
                }
            }

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
