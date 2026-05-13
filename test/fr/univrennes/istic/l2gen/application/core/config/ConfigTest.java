package fr.univrennes.istic.l2gen.application.core.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.prefs.BackingStoreException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigTest {

    private static final String TEST_KEY = "test_key_junit";

    @Before
    @After
    public void clearPrefs() throws BackingStoreException {
        Config.get().clear();
    }

    @Test
    public void testPutAndGetBoolean() {
        Config.putBoolean(TEST_KEY, true);
        assertTrue(Config.getBoolean(TEST_KEY, false));
    }

    @Test
    public void testPutAndGetInt() {
        Config.putInt(TEST_KEY, 42);
        assertEquals(42, Config.getInt(TEST_KEY, 0));
    }

    @Test
    public void testPutAndGetString() {
        Config.put(TEST_KEY, "Pangol1");
        assertEquals("Pangol1", Config.get(TEST_KEY, "default"));
    }

    @Test
    public void testPutAndGetByteArray() {
        byte[] data = { 1, 2, 3 };
        Config.putByteArray(TEST_KEY, data);
        assertArrayEquals(data, Config.getByteArray(TEST_KEY, new byte[0]));
    }

    @Test
    public void testPutIfAbsent() {
        Config.putIfAbsent(TEST_KEY, "Original");
        assertEquals("Original", Config.get(TEST_KEY, ""));

        // 2. On réessaie : la valeur ne doit pas changer
        Config.putIfAbsent(TEST_KEY, "Modified");
        assertEquals("Original", Config.get(TEST_KEY, ""));
    }

    @Test
    public void testPutBooleanIfAbsent() {
        Config.putBooleanIfAbsent(TEST_KEY, true);
        assertTrue(Config.getBoolean(TEST_KEY, false));

        Config.putBooleanIfAbsent(TEST_KEY, false);
        assertTrue(Config.getBoolean(TEST_KEY, false));
    }

    @Test
    public void testRemove() {
        Config.put(TEST_KEY, "To be removed");
        Config.remove(TEST_KEY);
        assertEquals("default", Config.get(TEST_KEY, "default"));
    }

    @Test
    public void testDefaultValues() {
        assertEquals("fallback", Config.get("non_existent_key", "fallback"));
        assertEquals(100, Config.getInt("non_existent_key", 100));
    }

    @Test
    public void testPutIntIfAbsent() {
        Config.putIntIfAbsent(TEST_KEY, 10);
        assertEquals(10, Config.getInt(TEST_KEY, 0));
        Config.putIntIfAbsent(TEST_KEY, 20);
        assertEquals(10, Config.getInt(TEST_KEY, 0));
    }

    @Test
    public void testPutFloatIfAbsent() {
        Config.putFloatIfAbsent(TEST_KEY, 1.5f);
        Assert.assertEquals(1.5f, Config.getFloat(TEST_KEY, 0.0f), 0.001f);
    }
}