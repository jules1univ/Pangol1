package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import org.junit.Test;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests pour SettingsSectionPanel.
 */
public class SettingsSectionPanelTest extends SettingsTestSupport {

    @Test
    public void testSectionHeaderAndRowsAreWired() throws Exception {
        SettingsSectionPanel section = new SettingsSectionPanel("General");

        JLabel label = findComponent(section, JLabel.class);
        assertNotNull(label);
        assertEquals("General", label.getText());

        JSeparator separator = findComponent(section, JSeparator.class);
        assertNotNull(separator);

        JPanel bodyPanel = getField(section, "bodyPanel", JPanel.class);
        assertEquals(0, bodyPanel.getComponentCount());

        JPanel row = new JPanel();
        section.addRow(row);
        assertEquals(1, bodyPanel.getComponentCount());
        assertEquals(row, bodyPanel.getComponent(0));
    }
}