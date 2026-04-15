package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingControlBuilder;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingRowPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSectionPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSeparatorRow;

public final class ChartSettingsPanel extends SettingSectionPanel {

        public ChartSettingsPanel() {
                super(Lang.get("report.setting.chart"));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.type"),
                                SettingControlBuilder.dropdown(new String[] {
                                                Lang.get("report.setting.chart.pie"),
                                                Lang.get("report.setting.chart.bar"),
                                                Lang.get("report.setting.chart.columns")
                                })));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.title"),
                                SettingControlBuilder.textField(Lang.get("report.setting.chart.default_title"))));

                addRow(new SettingSeparatorRow());

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.show_legend"),
                                SettingControlBuilder.checkbox(true)));

                addRow(new SettingSeparatorRow(Lang.get("report.setting.chart.axis")));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.tick_count"),
                                SettingControlBuilder.spinner(1, 50, 5, 1)));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.scale"),
                                SettingControlBuilder.dropdown(new String[] {
                                                Lang.get("report.setting.chart.linear"),
                                                Lang.get("report.setting.chart.logarithmic"),
                                                Lang.get("report.setting.chart.sqrt")
                                })));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.show_x_axis"),
                                SettingControlBuilder.checkbox(true)));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.x_label"),
                                SettingControlBuilder.textField(Lang.get("report.setting.chart.default_labelx"))));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.show_y_axis"),
                                SettingControlBuilder.checkbox(true)));

                addRow(new SettingRowPanel(Lang.get("report.setting.chart.y_label"),
                                SettingControlBuilder.textField(Lang.get("report.setting.chart.default_labely"))));
        }
}
