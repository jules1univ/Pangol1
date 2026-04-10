package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;

public final class AdvancedSettingsPanel extends AbstractSettingsPanel {

    private final JSpinner maxUndoStepsSpinner;
    private final JSpinner maxRowsInMemorySpinner;
    private final JCheckBox enableVirtualScrollingCheckBox;
    private final JComboBox<String> logLevelComboBox;
    private final JCheckBox writeLogFileCheckBox;
    private final JCheckBox enableDeveloperModeCheckBox;
    private final JCheckBox showInternalErrorsCheckBox;
    private final JCheckBox experimentalFeaturesCheckBox;
    private final JSpinner backgroundThreadsSpinner;

    public AdvancedSettingsPanel() {
        maxUndoStepsSpinner = new JSpinner(new SpinnerNumberModel(50, 5, 500, 5));

        maxRowsInMemorySpinner = new JSpinner(new SpinnerNumberModel(50000, 1000, 2000000, 1000));

        enableVirtualScrollingCheckBox = new JCheckBox();
        enableVirtualScrollingCheckBox.setOpaque(false);
        enableVirtualScrollingCheckBox.setSelected(true);

        logLevelComboBox = new JComboBox<>(new String[] { "ERROR", "WARN", "INFO", "DEBUG", "TRACE" });
        logLevelComboBox.setSelectedItem("WARN");

        writeLogFileCheckBox = new JCheckBox();
        writeLogFileCheckBox.setOpaque(false);
        writeLogFileCheckBox.setSelected(false);

        enableDeveloperModeCheckBox = new JCheckBox();
        enableDeveloperModeCheckBox.setOpaque(false);
        enableDeveloperModeCheckBox.setSelected(false);

        showInternalErrorsCheckBox = new JCheckBox();
        showInternalErrorsCheckBox.setOpaque(false);
        showInternalErrorsCheckBox.setSelected(false);

        experimentalFeaturesCheckBox = new JCheckBox();
        experimentalFeaturesCheckBox.setOpaque(false);
        experimentalFeaturesCheckBox.setSelected(false);

        backgroundThreadsSpinner = new JSpinner(new SpinnerNumberModel(
                Runtime.getRuntime().availableProcessors(),
                1,
                Runtime.getRuntime().availableProcessors() * 2,
                1));

        SettingsSectionPanel performanceSection = new SettingsSectionPanel(
                Lang.get("settings.advanced.section.performance"));
        performanceSection.addRow(
                new SettingsRowPanel(Lang.get("settings.advanced.performance.max_undo_steps"), maxUndoStepsSpinner));
        performanceSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.performance.max_rows_memory"),
                maxRowsInMemorySpinner));
        performanceSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.performance.virtual_scrolling"),
                enableVirtualScrollingCheckBox));
        performanceSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.performance.background_threads"),
                backgroundThreadsSpinner));

        SettingsSectionPanel loggingSection = new SettingsSectionPanel(Lang.get("settings.advanced.section.logging"));
        loggingSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.logging.level"), logLevelComboBox));
        loggingSection
                .addRow(new SettingsRowPanel(Lang.get("settings.advanced.logging.write_file"), writeLogFileCheckBox));

        SettingsSectionPanel developerSection = new SettingsSectionPanel(
                Lang.get("settings.advanced.section.developer"));
        developerSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.developer.enable_dev_mode"),
                enableDeveloperModeCheckBox));
        developerSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.developer.show_internal_errors"),
                showInternalErrorsCheckBox));
        developerSection.addRow(new SettingsRowPanel(Lang.get("settings.advanced.developer.experimental_features"),
                experimentalFeaturesCheckBox));

        addSection(performanceSection);
        addSection(loggingSection);
        addSection(developerSection);
    }

    @Override
    public void applySettings() {
    }
}
