package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.AbstractSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsRowPanel;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsSectionPanel;

public final class AppearanceSettingsPanel extends AbstractSettingsPanel {

        private final JComboBox<String> themeComboBox;
        private final JSlider themeStartHourSlider;
        private final JSlider themeEndHourSlider;

        private final JCheckBox useFlatLafCheckBox;
        private final JSlider fontSizeSlider;
        private final JComboBox<String> fontFamilyComboBox;

        public AppearanceSettingsPanel() {
                themeComboBox = new JComboBox<>(new String[] {
                                Lang.get("settings.appearance.theme.light"),
                                Lang.get("settings.appearance.theme.dark"),
                                Lang.get("settings.appearance.theme.system"),
                                Lang.get("settings.appearance.theme.auto")
                });

                themeStartHourSlider = new JSlider(0, 23, 18);
                themeStartHourSlider.setMajorTickSpacing(1);
                themeStartHourSlider.setPaintTicks(true);
                themeStartHourSlider.setPaintLabels(true);
                themeStartHourSlider.setEnabled(false);
                themeStartHourSlider.setLabelTable(themeStartHourSlider.createStandardLabels(6));
                themeStartHourSlider.setSnapToTicks(true);

                themeEndHourSlider = new JSlider(0, 23, 6);
                themeEndHourSlider.setMajorTickSpacing(1);
                themeEndHourSlider.setPaintTicks(true);
                themeEndHourSlider.setPaintLabels(true);
                themeEndHourSlider.setEnabled(false);
                themeEndHourSlider.setLabelTable(themeEndHourSlider.createStandardLabels(6));
                themeEndHourSlider.setSnapToTicks(true);

                themeComboBox.addActionListener(e -> {
                        String selected = (String) themeComboBox.getSelectedItem();
                        boolean auto = selected.equals(Lang.get("settings.appearance.theme.auto"));
                        themeStartHourSlider.setEnabled(auto);
                        themeEndHourSlider.setEnabled(auto);
                });

                useFlatLafCheckBox = new JCheckBox();
                useFlatLafCheckBox.setSelected(true);

                fontSizeSlider = new JSlider(8, 24, 12);
                fontSizeSlider.setMajorTickSpacing(1);
                fontSizeSlider.setPaintTicks(true);
                fontSizeSlider.setPaintLabels(true);
                fontSizeSlider.setLabelTable(fontSizeSlider.createStandardLabels(6));
                themeEndHourSlider.setSnapToTicks(true);

                List<String> fontFamilies = new ArrayList<>();
                fontFamilies.add(Lang.get("settings.appearance.ui.default_font"));
                for (String font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
                        fontFamilies.add(font);
                }
                fontFamilyComboBox = new JComboBox<>(fontFamilies.toArray(new String[0]));

                SettingsSectionPanel themeSection = new SettingsSectionPanel(
                                Lang.get("settings.appearance.section.theme"));
                themeSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.theme"), themeComboBox));
                themeSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.auto.start"),
                                themeStartHourSlider));
                themeSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.auto.end"), themeEndHourSlider));

                SettingsSectionPanel uiSection = new SettingsSectionPanel(Lang.get("settings.appearance.section.ui"));
                uiSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.ui.use_flatlaf"),
                                useFlatLafCheckBox));
                uiSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.ui.font_size"), fontSizeSlider));
                uiSection.addRow(new SettingsRowPanel(Lang.get("settings.appearance.ui.font_family"),
                                fontFamilyComboBox));

                addSection(themeSection);
                addSection(uiSection);
        }

        @Override
        public void applySettings() {
        }
}
