package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import org.junit.Test;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests pour AbstractSettingsPanel.
 */
public class AbstractSettingsPanelTest extends SettingsTestSupport {

    @Test
    public void testAddSectionAddsComponentToScrollContainer() throws Exception {
        ProbePanel panel = new ProbePanel();

        JScrollPane scrollPane = findComponent(panel, JScrollPane.class);
        JPanel body = getViewportViewPanel(scrollPane);
        assertEquals(0, body.getComponentCount());

        JPanel section = new JPanel();
        panel.addSection(section);
        assertEquals(1, body.getComponentCount());
        assertTrue(body.getComponent(0) == section);
    }

    private static final class ProbePanel extends AbstractSettingsPanel {
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