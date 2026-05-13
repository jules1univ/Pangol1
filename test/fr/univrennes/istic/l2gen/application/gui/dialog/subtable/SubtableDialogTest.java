package fr.univrennes.istic.l2gen.application.gui.dialog.subtable;

import org.junit.Test;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import java.awt.Frame;
import java.lang.reflect.Method;

import javax.swing.JDialog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests structurels pour SubtableDialog
 */
public class SubtableDialogTest {

    @Test
    public void testExtendsJDialogAndHasShowMethod() {
        assertTrue(JDialog.class.isAssignableFrom(SubtableDialog.class));
        assertHasMethod("show", Frame.class, DataTable.class);
    }

    private static void assertHasMethod(String name, Class<?>... types) {
        try {
            Method method = SubtableDialog.class.getMethod(name, types);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
