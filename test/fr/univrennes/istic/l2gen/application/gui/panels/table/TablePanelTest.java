package fr.univrennes.istic.l2gen.application.gui.panels.table;

import org.junit.Test;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;

import java.lang.reflect.Method;

import javax.swing.JPanel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests structurels pour TablePanel
 */
public class TablePanelTest {

    @Test
    public void testExtendsJPanelAndHasKeyMethods() {
        assertTrue(JPanel.class.isAssignableFrom(TablePanel.class));
        assertHasMethod("getTableView");
        assertHasMethod("getListView");
        assertHasMethod("open", DataTable.class);
        assertHasMethod("close");
        assertHasMethod("refresh");
    }

    private static void assertHasMethod(String name, Class<?>... types) {
        try {
            Method method = TablePanel.class.getMethod(name, types);
            assertNotNull(method);
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
