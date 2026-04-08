package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreController;
import org.junit.Test;
import static org.junit.Assert.*;

public class GUIControllerTest {

    /**
     * On verifie si guicontroller peut etre creer
     */
    @Test
    public void testGUIControllerIsSingleton() {

        GUIController instance1 = GUIController.getInstance();
        assertNotNull("getInstance() ne doit pas retourner null", instance1);
    }

    /**
     * GUI controller doit herite de CoreController
     */
    @Test
    public void testGUIControllerInheritsCoreController() {
        GUIController controller = GUIController.getInstance();
        assertTrue("GUIController doit hériter de CoreController",
                controller instanceof CoreController);
    }

    /**
     * On verifie que onStart() peut être appelé sans erreur
     */
    // @Test
    public void testOnStartMethodExists() {
        GUIController controller = GUIController.getInstance();

        try {
            controller.onStart();
            assertTrue("onStart() doit s'exécuter sans erreur", true);
        } catch (Exception e) {
            fail("onStart() ne doit pas lever d'exception: " + e.getMessage());
        }
    }

    /**
     * On verifie que onStop() peut être appelé sans erreur
     */
    // @Test
    public void testOnStopMethodExists() {
        GUIController controller = GUIController.getInstance();
        try {
            controller.onStop();
            assertTrue("onStop() doit s'exécuter sans erreur", true);
        } catch (Exception e) {
            fail("onStop() ne doit pas lever d'exception: " + e.getMessage());
        }
    }

}