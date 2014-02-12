package shiver.me.timbers.configuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static shiver.me.timbers.configuration.ConfigDir.DIR_NAME;
import static shiver.me.timbers.configuration.ConfigDir.create;
import static shiver.me.timbers.configuration.ConfigDir.createIfNecessary;
import static shiver.me.timbers.configuration.ConfigDir.homePath;
import static shiver.me.timbers.configuration.ConfigDir.path;

public class ConfigDirTest {

    @SuppressWarnings("ConstantConditions")
    private static final String BUILD_DIR = ConfigDirTest.class.getClassLoader().getResource("").getPath();

    private static final String HOME_DIR = System.getProperty("user.home");

    private static final String CONFIG_DIR = BUILD_DIR + DIR_NAME;

    @Before
    public void setUp() {

        deleteConfigDir();
    }

    @After
    public void tearDown() {

        deleteConfigDir();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteConfigDir() {

        configFile().delete();
    }

    private static File configFile() {

        return new File(CONFIG_DIR);
    }

    @Test
    public void testHomePathReturnsAValue() {

        assertNotNull("a path should be produced.", homePath());
    }

    @Test
    public void testHomePathCorrectPath() {

        assertEquals("the path should be correct.", HOME_DIR, homePath());
    }

    @Test
    public void testPathReturnsAValue() {

        assertNotNull("a path should be produced.", path());
    }

    @Test
    public void testPathCorrectPath() {

        assertEquals("the path should be correct.", HOME_DIR + "/" + DIR_NAME, path());
    }

    @Test
    public void testCreateProducesAValue() {

        assertNotNull("a file should be produced.", create(configFile()));
    }

    @Test
    public void testCreate() {

        createTest(new Callable<File>() {

            @Override
            public File call() throws Exception {

                return create(configFile());
            }
        });
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateTwice() {

        create(configFile());
        create(configFile());
    }

    @Test(expected = NullPointerException.class)
    public void testCreateWithNull() {

        create(null);
    }

    @Test
    public void testCreateIfNecessaryProducesAValue() {

        assertNotNull("a file should be produced.", createIfNecessary(CONFIG_DIR));
    }

    @Test
    public void testCreateIfNecessary() {

        createTest(new Callable<File>() {

            @Override
            public File call() throws Exception {

                return createIfNecessary(CONFIG_DIR);
            }
        });
    }

    @Test
    public void testCreateIfNecessaryTwice() {

        createTest(new Callable<File>() {

            @Override
            public File call() throws Exception {

                createIfNecessary(CONFIG_DIR);
                return createIfNecessary(CONFIG_DIR);
            }
        });
    }

    @Test(expected = IllegalStateException.class)
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void testCreateIfNecessaryOverFile() throws IOException {

        new File(CONFIG_DIR).createNewFile();

        createIfNecessary(CONFIG_DIR);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateIfNecessaryWithNull() {

        createIfNecessary(null);
    }

    public void createTest(Callable<File> callable) {

        assertFalse("the directory should not exists.", configFile().exists());

        final File configDir;

        try {

            configDir = callable.call();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

        assertNotNull("a file should be produced.", configDir);
        assertEquals("the path should be correct.", CONFIG_DIR, configDir.getPath());

        assertTrue("the directory should exists.", configFile().exists());
        assertTrue("the directory should be a directory.", configFile().isDirectory());
    }
}
