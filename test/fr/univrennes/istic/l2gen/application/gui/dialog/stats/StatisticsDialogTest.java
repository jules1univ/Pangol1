package fr.univrennes.istic.l2gen.application.gui.dialog.stats;

import org.junit.Test;

import java.awt.Frame;
import java.lang.reflect.Method;

import javax.swing.JDialog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests structurels pour StatisticsDialog
 */
public class StatisticsDialogTest {

    @Test
    public void testExtendsJDialogAndExposesNotebookFlag() {
        assertTrue(JDialog.class.isAssignableFrom(StatisticsDialog.class));
        assertHasMethod("isAddedToNotebook");
        assertHasConstructor(Frame.class, String.class, String.class);
    }

    private static void assertHasMethod(String name, Class<?>... types) {
        try {
            Method method = StatisticsDialog.class.getMethod(name, types);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }

    private static void assertHasConstructor(Class<?>... types) {
        try {
            assertNotNull(StatisticsDialog.class.getConstructor(types));
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
