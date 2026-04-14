package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class GeneralSettingsPanel extends AbstractSettingsPanel {

        private final JComboBox<String> languageComboBox;

        private final JCheckBox showWelcomeScreenCheckBox;
        private final JCheckBox checkUpdatesCheckBox;
        private final JCheckBox reopenLastTablesCheckBox;
        private final JComboBox<String> defaultTableSourceComboBox;

        private final JButton defaultTableSourceFileButton;
        private final JTextField defaultTableSourceTextField;

        private final JCheckBox confirmOnCloseCheckBox;
        private final JCheckBox confirmOnTableCloseCheckBox;

        public GeneralSettingsPanel() {
                SettingsSectionPanel localizationSection = new SettingsSectionPanel(
                                Lang.get("settings.general.section.localization"));

                List<String> languagesBox = new ArrayList<>();
                for (String lang : Lang.getSupportedLanguages()) {
                        Locale local = Locale.forLanguageTag(lang);

                        String displayName = local.getDisplayLanguage(local);
                        displayName = displayName.substring(0, 1).toUpperCase() + displayName.substring(1);

                        languagesBox.add(displayName + " (" + local.getLanguage().toUpperCase() + ")");
                }

                languageComboBox = new JComboBox<>(languagesBox.toArray(new String[0]));
                localizationSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.general.language"), languageComboBox));

                SettingsSectionPanel startupSection = new SettingsSectionPanel(
                                Lang.get("settings.general.section.startup"));

                showWelcomeScreenCheckBox = new JCheckBox();
                showWelcomeScreenCheckBox.setSelected(true);
                startupSection.addRow(new SettingsRowPanel(Lang.get("settings.general.startup.show_welcome"),
                                showWelcomeScreenCheckBox));

                checkUpdatesCheckBox = new JCheckBox();
                checkUpdatesCheckBox.setSelected(true);
                startupSection.addRow(new SettingsRowPanel(Lang.get("settings.general.startup.check_updates"),
                                checkUpdatesCheckBox));

                reopenLastTablesCheckBox = new JCheckBox();
                startupSection.addRow(new SettingsRowPanel(Lang.get("settings.general.startup.reopen_table"),
                                reopenLastTablesCheckBox));

                defaultTableSourceComboBox = new JComboBox<>(
                                new String[] {
                                                Lang.get("settings.general.startup.default.none"),
                                                Lang.get("settings.general.startup.default.from_file"),
                                                Lang.get("settings.general.startup.default.from_url")
                                });

                defaultTableSourceTextField = new JTextField();
                defaultTableSourceTextField.setPreferredSize(
                                new Dimension(SettingsRowPanel.LABEL_WIDTH, SettingsRowPanel.ROW_HEIGHT - 8));
                defaultTableSourceTextField.setVisible(false);

                defaultTableSourceFileButton = new JButton(Lang.get("settings.general.startup.default.file_chooser"));
                defaultTableSourceFileButton.setVisible(false);

                defaultTableSourceComboBox.addActionListener(e -> {
                        if (defaultTableSourceComboBox.getSelectedIndex() == 0) {
                                defaultTableSourceTextField.setText("");
                                defaultTableSourceTextField.setVisible(true);
                                defaultTableSourceFileButton.setVisible(false);
                        } else if (defaultTableSourceComboBox.getSelectedIndex() == 1) {
                                defaultTableSourceTextField.setText("");
                                defaultTableSourceTextField.setVisible(false);
                                defaultTableSourceFileButton.setVisible(true);
                        } else {
                                defaultTableSourceTextField.setText("");
                                defaultTableSourceTextField.setVisible(false);
                                defaultTableSourceFileButton.setVisible(false);
                        }
                });

                startupSection.addRow(new SettingsRowPanel(Lang.get("settings.general.startup.default_table"),
                                defaultTableSourceComboBox));
                startupSection.addRow(new SettingsRowPanel(null, defaultTableSourceTextField,
                                defaultTableSourceFileButton));

                SettingsSectionPanel closingSection = new SettingsSectionPanel(
                                Lang.get("settings.general.section.closing"));

                confirmOnCloseCheckBox = new JCheckBox();

                closingSection.addRow(new SettingsRowPanel(Lang.get("settings.general.closing.app_close"),
                                confirmOnCloseCheckBox));

                confirmOnTableCloseCheckBox = new JCheckBox();
                closingSection.addRow(new SettingsRowPanel(Lang.get("settings.general.closing.table_close"),
                                confirmOnTableCloseCheckBox));

                addSection(localizationSection);
                addSection(startupSection);
                addSection(closingSection);
        }

        @Override
        public void applySettings() {
        }
}
