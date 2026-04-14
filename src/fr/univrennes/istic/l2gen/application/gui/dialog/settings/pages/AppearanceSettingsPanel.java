package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class AppearanceSettingsPanel extends AbstractSettingsPanel {

        private final JComboBox<String> themeComboBox;
        private final JComboBox<String> accentColorComboBox;
        private final JComboBox<String> fontFamilyComboBox;
        private final JSpinner fontSizeSpinner;
        private final JCheckBox showGridLinesCheckBox;
        private final JCheckBox alternateRowColorsCheckBox;
        private final JSpinner rowHeightSpinner;
        private final JCheckBox showStatusBarCheckBox;
        private final JCheckBox showColumnStatsCheckBox;

        public AppearanceSettingsPanel() {
                themeComboBox = new JComboBox<>(new String[] { "System default", "Light", "Dark", "High contrast" });

                accentColorComboBox = new JComboBox<>(new String[] { "Blue", "Green", "Purple", "Orange", "Red" });

                fontFamilyComboBox = new JComboBox<>(new String[] {
                                "Dialog", "Monospaced", "SansSerif", "Serif", "Segoe UI", "SF Pro"
                });

                fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 24, 1));

                showGridLinesCheckBox = new JCheckBox();
                showGridLinesCheckBox.setOpaque(false);
                showGridLinesCheckBox.setSelected(true);

                alternateRowColorsCheckBox = new JCheckBox();
                alternateRowColorsCheckBox.setOpaque(false);
                alternateRowColorsCheckBox.setSelected(false);

                rowHeightSpinner = new JSpinner(new SpinnerNumberModel(22, 16, 60, 1));

                showStatusBarCheckBox = new JCheckBox();
                showStatusBarCheckBox.setOpaque(false);
                showStatusBarCheckBox.setSelected(true);

                showColumnStatsCheckBox = new JCheckBox();
                showColumnStatsCheckBox.setOpaque(false);
                showColumnStatsCheckBox.setSelected(true);

                SettingsSectionPanel themeSection = new SettingsSectionPanel(
                                Lang.get("settings.appearance.section.theme"));
                themeSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.theme"), themeComboBox));
                themeSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.accent_color"),
                                accentColorComboBox));

                SettingsSectionPanel fontSection = new SettingsSectionPanel(
                                Lang.get("settings.appearance.section.font"));
                fontSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.appearance.font.family"), fontFamilyComboBox));
                fontSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.font.size"), fontSizeSpinner));

                SettingsSectionPanel tableSection = new SettingsSectionPanel(
                                Lang.get("settings.appearance.section.table"));
                tableSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.appearance.table.show_grid"),
                                                showGridLinesCheckBox));
                tableSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.appearance.table.alternate_rows"),
                                                alternateRowColorsCheckBox));
                tableSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.table.row_height"),
                                rowHeightSpinner));

                SettingsSectionPanel uiSection = new SettingsSectionPanel(Lang.get("settings.appearance.section.ui"));
                uiSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.appearance.ui.show_status_bar"),
                                                showStatusBarCheckBox));
                uiSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.appearance.ui.show_column_stats"),
                                                showColumnStatsCheckBox));

                addSection(themeSection);
                addSection(fontSection);
                addSection(tableSection);
                addSection(uiSection);
        }

        @Override
        public void applySettings() {
        }
}
