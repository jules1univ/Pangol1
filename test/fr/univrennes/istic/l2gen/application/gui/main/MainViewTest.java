package fr.univrennes.istic.l2gen.application.gui.main;

import org.junit.Test;

import java.lang.reflect.Method;

import javax.swing.JFrame;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests structurels pour MainView
 */
public class MainViewTest {

    @Test
    public void testExtendsJFrameAndExposesKeyMethods() {
        assertTrue(JFrame.class.isAssignableFrom(MainView.class));

        assertHasMethod("getTablePanel");
        assertHasMethod("getReportPanel");
        assertHasMethod("getTopBar");
        assertHasMethod("getBottomBar");
        assertHasMethod("ready");
    }

    private static void assertHasMethod(String name) {
        try {
            Method method = MainView.class.getMethod(name);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
