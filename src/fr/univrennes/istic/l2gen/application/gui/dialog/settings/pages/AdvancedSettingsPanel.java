package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.GUIController;
import fr.univrennes.istic.l2gen.application.gui.dialog.dev.DevConsoleDialog;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class AdvancedSettingsPanel extends AbstractSettingsPanel {

        private final JCheckBox enableDebugLogCheckBox;

        private final JCheckBox enableDevModeCheckBox;
        private final JButton openDevConsoleButton;

        public AdvancedSettingsPanel() {

                enableDebugLogCheckBox = new JCheckBox();
                enableDebugLogCheckBox.setSelected(Config.getBoolean("settings.advanced.debug_log", false));

                enableDevModeCheckBox = new JCheckBox();
                enableDevModeCheckBox.setSelected(Config.getBoolean("settings.advanced.dev_mode", false));

                openDevConsoleButton = new JButton(Lang.get("settings.advanced.dev_mode.console.open"));
                openDevConsoleButton.setEnabled(enableDevModeCheckBox.isSelected());
                openDevConsoleButton.addActionListener(e -> {
                        DevConsoleDialog.show(GUIController.getInstance().getMainView());
                });

                SettingsSectionPanel logSection = new SettingsSectionPanel(Lang.get("settings.advanced.section.log"));
                logSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.debug_log"),
                                enableDebugLogCheckBox));

                SettingsSectionPanel devSection = new SettingsSectionPanel(Lang.get("settings.advanced.section.dev"));
                devSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.dev_mode"),
                                enableDevModeCheckBox));

                enableDevModeCheckBox.addActionListener(e -> {
                        boolean enabled = enableDevModeCheckBox.isSelected();
                        openDevConsoleButton.setEnabled(enabled);
                });

                devSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.dev_mode.console"),
                                openDevConsoleButton));

                addSection(logSection);
                addSection(devSection);
        }

        @Override
        public boolean applySettings() {
                boolean changed = false;
                if (Config.getBoolean("settings.advanced.debug_log", false) != enableDebugLogCheckBox
                                .isSelected()) {
                        changed = true;
                }
                if (Config.getBoolean("settings.advanced.dev_mode", false) != enableDevModeCheckBox
                                .isSelected()) {
                        changed = true;
                }

                Config.put("settings.advanced.debug_log", enableDebugLogCheckBox.isSelected());
                Config.put("settings.advanced.dev_mode", enableDevModeCheckBox.isSelected());

                return changed;
        }

        @Override
        public boolean requiresRestart() {
                return false;
        }
}
