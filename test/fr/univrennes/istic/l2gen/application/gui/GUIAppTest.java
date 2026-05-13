package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreApp;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * On regarde si ca respecte bien la structure
 */
public class GUIAppTest {

    /**
     * Verifie si GUIApp extends CoreApp
     */
    @Test
    public void testGUIAppExtendsCoreApp() {
        Class<?> guiClass = GUIApp.class;
        assertTrue("GUIApp must extend CoreApp", CoreApp.class.isAssignableFrom(guiClass));
    }

    /**
     * Verifie si GUIApp a un default constructor
     */
    @Test
    public void testGUIAppHasDefaultConstructor() {
        try {
            assertNotNull("GUIApp must have a default constructor", GUIApp.class.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            fail("GUIApp constructor cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie si GUIApp a une méthode start()
     */
    @Test
    public void testGUIAppHasStartMethod() {
        try {
            Method startMethod = GUIApp.class.getMethod("start");
            assertNotNull("GUIApp must have a start() method", startMethod);
        } catch (NoSuchMethodException e) {
            fail("GUIApp start() method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que le constructeur de GUIApp branche l'app sur le controller.
     */
    @Test
    public void testGUIAppConstructorWiresController() throws Exception {
        GUIApp app = new GUIApp();

        Field appField = GUIController.class.getDeclaredField("app");
        appField.setAccessible(true);

        Object wiredApp = appField.get(GUIController.getInstance());
        assertSame("GUIApp doit etre referencee par GUIController", app, wiredApp);
    }

}
