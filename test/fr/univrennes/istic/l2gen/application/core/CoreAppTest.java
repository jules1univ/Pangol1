package fr.univrennes.istic.l2gen.application.core;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Ici on test juste la stucture
 */
public class CoreAppTest {

    /**
     * On verifie que CoreApp existe et est bien abstraite et a une methode start()
     */
    @Test
    public void testCoreAppIsAbstract() {
        Class<?> classC = fr.univrennes.istic.l2gen.application.core.CoreApp.class;
        assertTrue("CoreApp doit etre abstraite", Modifier.isAbstract(classC.getModifiers()));
        try {
            classC.getMethod("start");
        } catch (NoSuchMethodException e) {
            fail("CoreApp start() method cannot be found: " + e.getMessage());
        }
    }

    /**
     * On verifie que CoreApp a un constructeur qui prend un Controller
     */
    @Test
    public void testCoreAppHasControllerConstructor() {
        Class<?> classC = fr.univrennes.istic.l2gen.application.core.CoreApp.class;
        Class<?> controllerClass = fr.univrennes.istic.l2gen.application.core.CoreController.class;
        try {
            assertNotNull("CoreApp doit avoir un constructeur qui prend un Controller", classC.getDeclaredConstructor(controllerClass));
        } catch (NoSuchMethodException e) {
            fail("CoreApp constructor with CoreController parameter cannot be found: " + e.getMessage());
        }
    }

    /**
     * On verifie que getController() existe et retourne un CoreController
     */
    @Test
    public void testCoreAppHasGetControllerMethod() {
        Class<?> classC = fr.univrennes.istic.l2gen.application.core.CoreApp.class;
        Class<?> controllerClass = fr.univrennes.istic.l2gen.application.core.CoreController.class;
        try {
            Method getcontroller = classC.getMethod("getController");
            assertTrue("getController() doit retourner un CoreController", controllerClass.isAssignableFrom(getcontroller.getReturnType()));
        } catch (NoSuchMethodException e) {
            fail("CoreApp getController() method cannot be found: " + e.getMessage());
        }
    }

    /**
     * On verifie qu'une implémentation concrète peut hériter de CoreApp
     */
    @Test
    public void testCoreAppCanBeExtended() {
        Class<?> coreAppClass = fr.univrennes.istic.l2gen.application.core.CoreApp.class;
        Class<?> guiAppClass = fr.univrennes.istic.l2gen.application.gui.GUIApp.class;
        assertTrue("GUIApp doit etendre CoreApp", coreAppClass.isAssignableFrom(guiAppClass));
    }

}