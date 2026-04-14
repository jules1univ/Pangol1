package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class DataImportSettingsPanel extends AbstractSettingsPanel {

        private final JComboBox<String> defaultEncodingComboBox;
        private final JComboBox<String> defaultDelimiterComboBox;
        private final JTextField customDelimiterField;
        private final JComboBox<String> quoteCharComboBox;
        private final JCheckBox firstRowAsHeaderCheckBox;
        private final JCheckBox trimWhitespaceCheckBox;
        private final JCheckBox skipEmptyLinesCheckBox;
        private final JSpinner previewRowCountSpinner;
        private final JCheckBox inferTypesCheckBox;
        private final JCheckBox parseDatesCheckBox;
        private final JTextField dateFormatField;

        public DataImportSettingsPanel() {
                defaultEncodingComboBox = new JComboBox<>(new String[] {
                                "UTF-8", "UTF-16", "ISO-8859-1", "Windows-1252", "ASCII"
                });

                defaultDelimiterComboBox = new JComboBox<>(new String[] {
                                "Comma (,)", "Semicolon (;)", "Tab (\\t)", "Pipe (|)", "Custom"
                });

                customDelimiterField = new JTextField(4);

                quoteCharComboBox = new JComboBox<>(new String[] { "Double quote (\")", "Single quote (')", "None" });

                firstRowAsHeaderCheckBox = new JCheckBox();
                firstRowAsHeaderCheckBox.setOpaque(false);
                firstRowAsHeaderCheckBox.setSelected(true);

                trimWhitespaceCheckBox = new JCheckBox();
                trimWhitespaceCheckBox.setOpaque(false);
                trimWhitespaceCheckBox.setSelected(true);

                skipEmptyLinesCheckBox = new JCheckBox();
                skipEmptyLinesCheckBox.setOpaque(false);
                skipEmptyLinesCheckBox.setSelected(true);

                previewRowCountSpinner = new JSpinner(new SpinnerNumberModel(100, 10, 10000, 10));

                inferTypesCheckBox = new JCheckBox();
                inferTypesCheckBox.setOpaque(false);
                inferTypesCheckBox.setSelected(true);

                parseDatesCheckBox = new JCheckBox();
                parseDatesCheckBox.setOpaque(false);
                parseDatesCheckBox.setSelected(true);

                dateFormatField = new JTextField("yyyy-MM-dd");

                SettingsSectionPanel encodingSection = new SettingsSectionPanel(
                                Lang.get("settings.data_import.section.encoding"));
                encodingSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.data_import.encoding.default"),
                                                defaultEncodingComboBox));

                SettingsSectionPanel delimiterSection = new SettingsSectionPanel(
                                Lang.get("settings.data_import.section.delimiter"));
                delimiterSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.data_import.delimiter.default"),
                                                defaultDelimiterComboBox));
                delimiterSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.data_import.delimiter.custom"),
                                                customDelimiterField));
                delimiterSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.data_import.delimiter.quote_char"),
                                                quoteCharComboBox));

                SettingsSectionPanel parsingSection = new SettingsSectionPanel(
                                Lang.get("settings.data_import.section.parsing"));
                parsingSection.addRow(new SettingsRowPanel(Lang.get("settings.data_import.parsing.first_row_header"),
                                firstRowAsHeaderCheckBox));
                parsingSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.data_import.parsing.trim_whitespace"),
                                                trimWhitespaceCheckBox));
                parsingSection.addRow(new SettingsRowPanel(Lang.get("settings.data_import.parsing.skip_empty_lines"),
                                skipEmptyLinesCheckBox));
                parsingSection.addRow(
                                new SettingsRowPanel(Lang.get("settings.data_import.parsing.preview_rows"),
                                                previewRowCountSpinner));

                SettingsSectionPanel typeInferenceSection = new SettingsSectionPanel(
                                Lang.get("settings.data_import.section.type_inference"));
                typeInferenceSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.data_import.types.infer"),
                                                inferTypesCheckBox));
                typeInferenceSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.data_import.types.parse_dates"),
                                                parseDatesCheckBox));
                typeInferenceSection
                                .addRow(new SettingsRowPanel(Lang.get("settings.data_import.types.date_format"),
                                                dateFormatField));

                addSection(encodingSection);
                addSection(delimiterSection);
                addSection(parsingSection);
                addSection(typeInferenceSection);
        }

        @Override
        public void applySettings() {
        }
}
