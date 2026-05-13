package fr.univrennes.istic.l2gen.application.core.config;

import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class LogTest {

    @Before
    public void setup() {
        Log.DEBUG_MODE = false;
        Config.remove("settings.advanced.debug_log");
    }

    @Test
    public void testDebugRunnableInDebugMode() {
        Log.DEBUG_MODE = true;
        AtomicBoolean ran = new AtomicBoolean(false);

        Log.debug(() -> ran.set(true));

        assertTrue("Le runnable devrait être exécuté en mode DEBUG", ran.get());
    }

    @Test
    public void testDebugRunnableInProdMode() {
        Log.DEBUG_MODE = false;
        AtomicBoolean ran = new AtomicBoolean(false);

        Log.debug(() -> ran.set(true));

        assertFalse("Le runnable ne devrait pas être exécuté en mode PROD", ran.get());
    }

    @Test
    public void testModeSwitch() {
        AtomicBoolean dbgRan = new AtomicBoolean(false);
        AtomicBoolean prodRan = new AtomicBoolean(false);

        Log.DEBUG_MODE = true;
        Log.mode(() -> dbgRan.set(true), () -> prodRan.set(true));
        assertTrue(dbgRan.get());
        assertFalse(prodRan.get());

        dbgRan.set(false);
        prodRan.set(false);

        Log.DEBUG_MODE = false;
        Log.mode(() -> dbgRan.set(true), () -> prodRan.set(true));
        assertFalse(dbgRan.get());
        assertTrue(prodRan.get());
    }

    @Test
    public void testDebugMessageWithConfigOverride() {
        Log.DEBUG_MODE = false;
        Config.putBoolean("settings.advanced.debug_log", true);
        Log.debug("Message de test");
    }

    @Test
    public void testDebugMessageWithException() {
        Log.DEBUG_MODE = true;
        Exception e = new Exception("Test exception");
        Log.debug("Erreur détectée", e);
    }
}