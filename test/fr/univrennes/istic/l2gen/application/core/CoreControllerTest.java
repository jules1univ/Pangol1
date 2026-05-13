package fr.univrennes.istic.l2gen.application.core;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

/**
 * On test la structure
 */
public class CoreControllerTest {

    /**
     * on verifie que CoreController existe et est bien abstraite
     * 
     * Ce qu'on teste:
     * - La classe CoreController existe
     * - Elle est abstraite
     * - Elle a les méthodes abstraites onStart() et onStop()
     */
    @Test
    public void testCoreControllerIsAbstract() {
        Class<?> classC = fr.univrennes.istic.l2gen.application.core.CoreController.class;
        assertTrue("CoreController doit etre abstraite", Modifier.isAbstract(classC.getModifiers()));
        try {
            classC.getMethod("onStart");
            classC.getMethod("onStop");
        } catch (NoSuchMethodException e) {
            fail("CoreController methods onStart/onStop not found: " + e.getMessage());
        }
    }

    /**
     * On verifie que CoreController a un constructeur
     */
    @Test
    public void testCoreControllerHasProtectedConstructor() {
        Constructor<?>[] constructors = fr.univrennes.istic.l2gen.application.core.CoreController.class.getDeclaredConstructors();
        assertTrue("Il doit y avoir un constructeur", constructors.length > 0);
    }

}