package fr.univrennes.istic.l2gen.application.gui.main;

import org.junit.Test;

import java.lang.reflect.Method;

import javax.swing.JWindow;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests pour SplashScreen
 */
public class SplashScreenTest {

    @Test
    public void testExtendsJWindowAndHasActions() {
        assertTrue(JWindow.class.isAssignableFrom(SplashScreen.class));
        assertHasMethod("setStatus", String.class);
        assertHasMethod("close");
    }

    private static void assertHasMethod(String name, Class<?>... types) {
        try {
            Method method = SplashScreen.class.getMethod(name, types);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
