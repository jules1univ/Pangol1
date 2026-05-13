package fr.univrennes.istic.l2gen.application.gui.panels.table.view.list;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Locale;

import fr.univrennes.istic.l2gen.application.gui.panels.table.TablePanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests pour TableListView
 */
public class TableListViewTest {

    @Test
    public void testIsEmptyOnNewViewAndFormatSize() throws Exception {
        TableListView view = new TableListView((TablePanel) null);

        assertTrue(view.isEmpty());

        Method formatSize = TableListView.class.getDeclaredMethod("formatSize", long.class);
        formatSize.setAccessible(true);

        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            assertEquals("512 B", formatSize.invoke(view, 512L));
            assertEquals("2.0 KB", formatSize.invoke(view, 2048L));
        } finally {
            Locale.setDefault(previous);
        }
    }
}
