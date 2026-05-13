package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests de structure pour SettingsPage.
 */
public class SettingsPageTest {

    @Test
    public void testInterfaceDeclaresExpectedMethods() throws Exception {
        assertTrue(SettingsPage.class.isInterface());
        assertTrue(SettingsPage.class.getMethod("applySettings").getReturnType() == boolean.class);
        assertTrue(SettingsPage.class.getMethod("requiresRestart").getReturnType() == boolean.class);

        DummyPage page = new DummyPage();
        assertFalse(page.applySettings());
        assertFalse(page.requiresRestart());
    }

    private static final class DummyPage implements SettingsPage {
        @Override
        public boolean applySettings() {
            return false;
        }

        @Override
        public boolean requiresRestart() {
            return false;
        }
    }
}