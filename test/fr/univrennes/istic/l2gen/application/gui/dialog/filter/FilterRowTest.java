package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JPanel;

import fr.univrennes.istic.l2gen.application.core.filter.Filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Tests pour FilterRow
 */
public class FilterRowTest {

    @Test
    public void testPanelAndFilterAccessors() {
        Filter filter = Filter.equals(1, "abc");
        FilterRow row = new FilterRow(null, "Description", filter);

        JPanel panel = row.getPanel();
        assertSame(filter, row.getFilter());
        assertEquals(2, panel.getComponentCount());

        JButton button = (JButton) panel.getComponent(1);
        assertEquals(1, button.getActionListeners().length);
    }
}
