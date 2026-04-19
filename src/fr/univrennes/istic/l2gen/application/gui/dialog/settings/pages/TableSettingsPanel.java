package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JCheckBox;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class TableSettingsPanel extends AbstractSettingsPanel {

    private final JCheckBox manualTypingCheckBox;
    private final JCheckBox readOnlyCheckBox;
    private final JCheckBox showRowNumbersCheckBox;
    private final JCheckBox showNullValuesCheckBox;
    private final JCheckBox stickyHeadersCheckBox;

    private final JCheckBox hideEmptyColumnsCheckBox;
    private final JCheckBox showColumnTypesCheckBox;
    private final JCheckBox autoResizeColumnsCheckBox;
    private final JCheckBox calculateStatisticsCheckBox;

    public TableSettingsPanel() {

        SettingsSectionPanel modeSection = new SettingsSectionPanel(
                Lang.get("settings.table.section.mode"));

        readOnlyCheckBox = new JCheckBox();
        modeSection.addRow(new SettingsRowPanel(Lang.get("settings.table.read_only"), readOnlyCheckBox));

        manualTypingCheckBox = new JCheckBox();
        modeSection.addRow(new SettingsRowPanel(Lang.get("settings.table.manual_typing"), manualTypingCheckBox));

        SettingsSectionPanel displaySection = new SettingsSectionPanel(
                Lang.get("settings.table.section.display"));

        showRowNumbersCheckBox = new JCheckBox();
        displaySection
                .addRow(new SettingsRowPanel(Lang.get("settings.table.show_row_numbers"), showRowNumbersCheckBox));

        showNullValuesCheckBox = new JCheckBox();
        displaySection
                .addRow(new SettingsRowPanel(Lang.get("settings.table.show_null_values"), showNullValuesCheckBox));

        stickyHeadersCheckBox = new JCheckBox();
        displaySection.addRow(new SettingsRowPanel(Lang.get("settings.table.sticky_headers"), stickyHeadersCheckBox));

        SettingsSectionPanel columnsSection = new SettingsSectionPanel(
                Lang.get("settings.table.section.columns"));

        hideEmptyColumnsCheckBox = new JCheckBox();
        columnsSection
                .addRow(new SettingsRowPanel(Lang.get("settings.table.columns.hide_empty"), hideEmptyColumnsCheckBox));

        showColumnTypesCheckBox = new JCheckBox();
        columnsSection
                .addRow(new SettingsRowPanel(Lang.get("settings.table.columns.show_types"), showColumnTypesCheckBox));

        autoResizeColumnsCheckBox = new JCheckBox();
        columnsSection.addRow(
                new SettingsRowPanel(Lang.get("settings.table.columns.auto_resize"), autoResizeColumnsCheckBox));

        SettingsSectionPanel statsSection = new SettingsSectionPanel(
                Lang.get("settings.table.section.statistics"));

        calculateStatisticsCheckBox = new JCheckBox();
        statsSection.addRow(new SettingsRowPanel(
                Lang.get("settings.table.columns.calculate_statistics"),
                calculateStatisticsCheckBox));

        addSection(modeSection);
        addSection(displaySection);
        addSection(columnsSection);
        addSection(statsSection);
    }

    @Override
    public void applySettings() {
    }
}
