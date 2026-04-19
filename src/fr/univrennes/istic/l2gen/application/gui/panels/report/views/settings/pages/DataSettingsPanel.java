package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComboBox;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.services.TableService;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.gui.GUIController;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingControlBuilder;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingRowPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSectionPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSeparatorRow;

public final class DataSettingsPanel extends SettingSectionPanel {

    public DataSettingsPanel() {
        super(Lang.get("report.setting.data"));

        build();
    }

    private void build() {
        List<String> tables = TableService.get().stream().map(table -> {
            if (table.getPath().equals(GUIController.getInstance().getTable().map(t -> t.getPath()).orElse(null))) {
                return Lang.get("report.setting.data.current_table");
            } else {
                return table.getPath().toString();
            }
        }).collect(Collectors.toList());

        JComboBox<String> selectTable = SettingControlBuilder.dropdown(tables.toArray(new String[0]));
        selectTable.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                File selectedTable = new File((String) selectTable.getSelectedItem());
                DataTable table = TableService.get(selectedTable);
                if (table != null) {
                    GUIController.getInstance().setTable(table);

                    removeAll();
                    build();
                }
            }
        });

        addRow(new SettingRowPanel(Lang.get("report.setting.data.table"), selectTable));

        String[] cols = GUIController.getInstance().getTable().map(table -> {
            final int size = (int) table.getColumnCount();
            String[] names = new String[size];
            for (int i = 0; i < size; i++) {
                names[i] = table.getColumnName(i);
            }
            return names;
        }).orElse(new String[] {});

        String[] colsWithNone = new String[cols.length + 1];
        System.arraycopy(cols, 0, colsWithNone, 0, cols.length);
        colsWithNone[cols.length] = Lang.get("report.setting.data.none");

        addRow(new SettingRowPanel(Lang.get("report.setting.data.col_group"),
                SettingControlBuilder.dropdown(colsWithNone)));

        addRow(new SettingRowPanel(Lang.get("report.setting.data.col_value"),
                SettingControlBuilder.dropdown(cols)));

        addRow(new SettingSeparatorRow());

        addRow(new SettingRowPanel(Lang.get("report.setting.data.filter_include"),
                SettingControlBuilder.checkbox(true)));

    }
}
