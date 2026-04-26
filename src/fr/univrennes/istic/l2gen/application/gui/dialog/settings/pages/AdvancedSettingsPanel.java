package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JCheckBox;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class AdvancedSettingsPanel extends AbstractSettingsPanel {

        private final JCheckBox enableDebugLogCheckBox;

        private final JCheckBox enableDevModeCheckBox;

        public AdvancedSettingsPanel() {

                enableDebugLogCheckBox = new JCheckBox();

                enableDevModeCheckBox = new JCheckBox();

                SettingsSectionPanel logSection = new SettingsSectionPanel(Lang.get("settings.advanced.section.log"));
                logSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.debug_log"),
                                enableDebugLogCheckBox));

                SettingsSectionPanel devSection = new SettingsSectionPanel(Lang.get("settings.advanced.section.dev"));
                devSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.dev_mode"),
                                enableDevModeCheckBox));

                addSection(logSection);
                addSection(devSection);
        }

        @Override
        public void applySettings() {
        }
}
