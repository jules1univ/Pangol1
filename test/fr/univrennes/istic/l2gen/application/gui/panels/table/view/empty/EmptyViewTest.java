package fr.univrennes.istic.l2gen.application.gui.panels.table.view.empty;

import org.junit.Test;

import javax.swing.JButton;

import fr.univrennes.istic.l2gen.application.core.config.Lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests pour EmptyView
 */
public class EmptyViewTest {

    @Test
    public void testContainsLoadButton() {
        EmptyView view = new EmptyView();

        assertEquals(1, view.getComponentCount());
        JButton button = (JButton) view.getComponent(0);
        assertNotNull(button);
        assertEquals(Lang.get("table.empty.load"), button.getText());
    }
}
