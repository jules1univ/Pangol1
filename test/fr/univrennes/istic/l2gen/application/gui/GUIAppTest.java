package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreApp;
import org.junit.Test;
import static org.junit.Assert.*;

public class GUIAppTest {

    /**
     * On verifie que GUIApp peut être créée avec le constructeur par defaut
     */
    @Test
    public void testGUIApp() {
        GUIApp app = new GUIApp();
        assertNotNull("GUIApp doit pouvoir être instanciée avec le constructeur par défaut", app);
    }

    /**
     * On verifie que GUIApp peut être créée avec un controleur specifique
     */
    @Test
    public void testGUIAppController() {
        GUIController controller = GUIController.getInstance();
        GUIApp app = new GUIApp(controller);

        assertNotNull("GUIApp doit pouvoir être instanciée avec un contrôleur", app);
    }

    /**
     * On verifie que GUIApp hérite de CoreApp
     */
    @Test
    public void testGUIAppInheritsCoreApp() {
        GUIApp app = new GUIApp();

        assertTrue("GUIApp doit hériter de CoreApp",
                app instanceof CoreApp);
    }

    /**
     * On verifie que getController() retourne un GUIController
     * 
     * getController() doit retourner un contrôleur valide
     * Le contrôleur est un GUIController
     */
    @Test
    public void testGUIAppGetController() {
        GUIApp app = new GUIApp();

        assertNotNull("getController() ne doit pas retourner null", app.getController());
    }

    /**
     * On verifie que celui creer et renvoyé est le meme
     */
    @Test
    public void testGUIAppPreservesCustomController() {
        GUIController controller = GUIController.getInstance();
        GUIApp app = new GUIApp(controller);

        assertEquals("GUIApp doit conserver le contrôleur passé en paramètre",
                controller, app.getController());
    }

    /**
     * On verifie que start() existe et ne plante pas
     */
    @Test
    public void testGUIAppStartMethodExists() {
        GUIApp app = new GUIApp();

        try {
            assertNotNull("GUIApp doit avoir une méthode start()",
                    app.getClass().getMethod("start"));
        } catch (NoSuchMethodException e) {
            fail("GUIApp doit avoir une méthode start(): " + e.getMessage());
        }
    }
}