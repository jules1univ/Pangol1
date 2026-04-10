package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;

public final class GeneralSettingsPanel extends AbstractSettingsPanel {

    private final JComboBox<String> languageComboBox;
    private final JCheckBox startupOpenLastFileCheckBox;
    private final JCheckBox startupShowWelcomeCheckBox;
    private final JCheckBox autosaveEnabledCheckBox;
    private final JSpinner autosaveIntervalSpinner;
    private final JCheckBox confirmOnCloseCheckBox;
    private final JCheckBox checkUpdatesCheckBox;

    public GeneralSettingsPanel() {
        languageComboBox = new JComboBox<>(new String[] { "English", "Français", "Deutsch", "Español" });

        startupOpenLastFileCheckBox = new JCheckBox();
        startupOpenLastFileCheckBox.setOpaque(false);
        startupOpenLastFileCheckBox.setSelected(true);

        startupShowWelcomeCheckBox = new JCheckBox();
        startupShowWelcomeCheckBox.setOpaque(false);
        startupShowWelcomeCheckBox.setSelected(false);

        autosaveEnabledCheckBox = new JCheckBox();
        autosaveEnabledCheckBox.setOpaque(false);
        autosaveEnabledCheckBox.setSelected(true);

        autosaveIntervalSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 60, 1));

        confirmOnCloseCheckBox = new JCheckBox();
        confirmOnCloseCheckBox.setOpaque(false);
        confirmOnCloseCheckBox.setSelected(true);

        checkUpdatesCheckBox = new JCheckBox();
        checkUpdatesCheckBox.setOpaque(false);
        checkUpdatesCheckBox.setSelected(true);

        SettingsSectionPanel localizationSection = new SettingsSectionPanel(
                Lang.get("settings.general.section.localization"));
        localizationSection.addRow(new SettingsRowPanel(Lang.get("settings.general.language"), languageComboBox));

        SettingsSectionPanel startupSection = new SettingsSectionPanel(Lang.get("settings.general.section.startup"));
        startupSection.addRow(
                new SettingsRowPanel(Lang.get("settings.general.startup.open_last_file"), startupOpenLastFileCheckBox));
        startupSection.addRow(
                new SettingsRowPanel(Lang.get("settings.general.startup.show_welcome"), startupShowWelcomeCheckBox));

        SettingsSectionPanel autosaveSection = new SettingsSectionPanel(Lang.get("settings.general.section.autosave"));
        autosaveSection
                .addRow(new SettingsRowPanel(Lang.get("settings.general.autosave.enabled"), autosaveEnabledCheckBox));
        autosaveSection.addRow(
                new SettingsRowPanel(Lang.get("settings.general.autosave.interval_minutes"), autosaveIntervalSpinner));

        SettingsSectionPanel behaviorSection = new SettingsSectionPanel(Lang.get("settings.general.section.behavior"));
        behaviorSection.addRow(
                new SettingsRowPanel(Lang.get("settings.general.behavior.confirm_on_close"), confirmOnCloseCheckBox));
        behaviorSection.addRow(
                new SettingsRowPanel(Lang.get("settings.general.behavior.check_updates"), checkUpdatesCheckBox));

        addSection(localizationSection);
        addSection(startupSection);
        addSection(autosaveSection);
        addSection(behaviorSection);
    }

    @Override
    public void applySettings() {
    }
}
