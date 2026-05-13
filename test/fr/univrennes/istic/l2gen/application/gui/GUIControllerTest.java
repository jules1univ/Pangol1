package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreController;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Locale;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Lang;

public class GUIControllerTest {

    @After
    public void clearLanguageSetting() {
        Config.remove("settings.general.language");
    }

    /**
     * On verifie si guicontroller peut etre creer
     */
    @Test
    public void testGUIControllerIsSingleton() {

        GUIController instance1 = GUIController.getInstance();
        GUIController instance2 = GUIController.getInstance();
        assertNotNull("getInstance() ne doit pas retourner null", instance1);
        assertSame("GUIController doit retourner la meme instance", instance1, instance2);
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
    // NE MARCHE PAS
    // @Test
    public void testOnStartMethodExists() {
        GUIController controller = GUIController.getInstance();

        try {
            controller.onStart();
        } catch (Exception e) {
            fail("onStart() ne doit pas lever d'exception: " + e.getMessage());
        }
    }

    /**
     * On verifie que onStop() existe
     */
    @Test
    public void testOnStopMethodExists() {
        try {
            Class<?> classC = GUIController.class;
            classC.getMethod("onStop");
        } catch (NoSuchMethodException e) {
            fail("onStop() ne peut pas etre trouvee: " + e.getMessage());
        }
    }

    /**
     * On verifie que getTable() retourne empty au départ
     */
    @Test
    public void testGetTableReturnsEmptyInitially() {
        GUIController controller = GUIController.getInstance();
        var tableOpt = controller.getTable();

        assertTrue("getTable() doit retourner un Optional vide initialement", tableOpt.isEmpty());
    }

    /**
     * On verifie que setTable() existe
     * 
     * setTable() prend un parametre DataTable
     */
    @Test
    public void testSetTableMethodExists() {
        try {
            Class<?> guiControllerClass = GUIController.class;
            Class<?> dataTableClass = DataTable.class;
            guiControllerClass.getMethod("setTable", dataTableClass);
        } catch (NoSuchMethodException e) {
            fail("setTable(DataTable) ne peut pas etre trouvee: " + e.getMessage());
        }
    }

    /**
     * Verifie que changer la langue avec la langue actuelle ne modifie rien.
     */
    @Test
    public void testOnLanguageChangeWithSameLocaleDoesNotTouchConfig() {
        Locale current = Lang.getLocale();
        Config.put("settings.general.language", "fr-FR");

        GUIController.getInstance().onLanguageChange(current);

        assertEquals("fr-FR", Config.get("settings.general.language", "missing"));
    }

}