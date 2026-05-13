package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import org.junit.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests pour SettingsRowPanel.
 */
public class SettingsRowPanelTest {

    @Test
    public void testRowWithLabelAndControl() {
        JButton button = new JButton("OK");
        SettingsRowPanel row = new SettingsRowPanel("Title", button);

        JLabel label = findComponent(row, JLabel.class);
        assertNotNull(label);
        assertEquals("Title", label.getText());

        JPanel controlsPanel = findControlsPanel(row);
        assertNotNull(controlsPanel);
        assertEquals(1, controlsPanel.getComponentCount());
        assertEquals(button, controlsPanel.getComponent(0));
    }

    @Test
    public void testRowWithoutLabelOnlyUsesControlsPanel() {
        JButton left = new JButton("A");
        JButton right = new JButton("B");
        SettingsRowPanel row = new SettingsRowPanel(null, left, right);

        assertNull(findComponent(row, JLabel.class));

        JPanel controlsPanel = findControlsPanel(row);
        assertNotNull(controlsPanel);
        assertEquals(2, controlsPanel.getComponentCount());
        assertEquals(left, controlsPanel.getComponent(0));
        assertEquals(right, controlsPanel.getComponent(1));
    }

    private static JPanel findControlsPanel(SettingsRowPanel row) {
        for (java.awt.Component component : row.getComponents()) {
            if (component instanceof JPanel panel) {
                return panel;
            }
        }
        return null;
    }

    private static <T extends java.awt.Component> T findComponent(java.awt.Container root, Class<T> type) {
        for (java.awt.Component component : root.getComponents()) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
            if (component instanceof java.awt.Container container) {
                T found = findComponent(container, type);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }
}