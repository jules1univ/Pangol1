package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.chart;

import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.core.services.table.TableService;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.gui.GUIController;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingRowPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSectionPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSeparatorRow;

public final class DataSettingsPanel extends SettingSectionPanel {

    private JComboBox<String> tables;
    private JComboBox<String> biggerGroupColumn;
    private JComboBox<String> groupColumn;
    private JComboBox<String> valueColumn;

    private Set<Integer> removedGroupColumns = new TreeSet<>();
    private Set<Integer> removedValueColumns = new TreeSet<>();

    private File currentTablePath = null;

    private JCheckBox filterInclude;
    private JCheckBox percentageCheck;

    private SharedChartSettings shared;

    public DataSettingsPanel() {
        super(Lang.get("report.settings.data"));
        build();
    }

    public void setShared(SharedChartSettings shared) {
        this.shared = shared;
    }

    private void build() {
        removedGroupColumns.clear();
        removedValueColumns.clear();
        currentTablePath = null;

        List<String> tablesPath = new ArrayList<>();
        DataTable currentTable = GUIController.getInstance().getTable().orElse(null);
        if (currentTable != null) {
            currentTablePath = currentTable.getPath();
            tablesPath.add(Lang.get("report.settings.data.current_table"));
        }
        tablesPath.addAll(TableService.get().stream()
                .map(DataTable::getPath)
                .map(File::toString)
                .filter(p -> currentTablePath == null || !p.equals(currentTablePath.toString()))
                .collect(Collectors.toList()));

        tables = new JComboBox<>(tablesPath.toArray(new String[0]));
        tables.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateColumnComboBoxes();
            }
        });
        addRow(new SettingRowPanel(Lang.get("report.settings.data.table"), tables));

        filterInclude = new JCheckBox();
        filterInclude.setSelected(true);
        addRow(new SettingRowPanel(Lang.get("report.settings.data.filter_include"), filterInclude));

        percentageCheck = new JCheckBox();
        percentageCheck.setSelected(false);
        addRow(new SettingRowPanel(Lang.get("report.settings.data.percentage"), percentageCheck));

        addRow(new SettingSeparatorRow(Lang.get("report.settings.data.columns")));

        valueColumn = new JComboBox<>(new String[0]);
        valueColumn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateLabels();
            }
        });
        addRow(new SettingRowPanel(Lang.get("report.settings.data.col_value"), valueColumn));

        groupColumn = new JComboBox<>(new String[0]);
        groupColumn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updateLabels();
            }
        });
        addRow(new SettingRowPanel(Lang.get("report.settings.data.col_group"), groupColumn));

        biggerGroupColumn = new JComboBox<>(new String[0]);
        addRow(new SettingRowPanel(Lang.get("report.settings.data.col_bigger_group"), biggerGroupColumn));

        updateColumnComboBoxes();
        updateLabels();
    }

    private void updateColumnComboBoxes() {
        removedGroupColumns.clear();
        removedValueColumns.clear();

        DataTable table = getTable();
        if (table == null) {
            return;
        }

        String[] valueCols = getColumnNames((t, i) -> {
            if (t.getColumnType(i).isNumeric())
                return true;
            removedValueColumns.add(i);
            return false;
        }, true);

        valueColumn.setModel(new JComboBox<>(valueCols).getModel());
        valueColumn.setEnabled(valueCols.length > 0);

        String[] groupCols = getColumnNames((t, i) -> {
            if (t.getColumnType(i).isCategorical())
                return true;
            removedGroupColumns.add(i);
            return false;
        }, false);

        groupColumn.setModel(new JComboBox<>(groupCols).getModel());
        groupColumn.setEnabled(groupCols.length > 0);

        String[] allCols = getColumnNames((t, i) -> true, true);
        String[] colsWithNone = new String[allCols.length + 1];
        colsWithNone[0] = Lang.get("report.settings.data.col_none");
        System.arraycopy(allCols, 0, colsWithNone, 1, allCols.length);

        biggerGroupColumn.setModel(new JComboBox<>(colsWithNone).getModel());
    }

    private void updateLabels() {
        if (shared == null)
            return;

        DataTable table = getTable();
        if (table == null)
            return;

        int valueIdx = getValueColumn();
        int groupIdx = getGroupColumn();

        String xLabel = table.getColumnName(valueIdx);
        String yLabel = table.getColumnName(groupIdx);

        if (xLabel != null && shared.axis().isXVisible()) {
            shared.axis().setXLabel(xLabel);
        }
        if (yLabel != null && shared.axis().isYVisible()) {
            shared.axis().setYLabel(yLabel);
        }

        if (xLabel != null && yLabel != null) {
            shared.chart().getTitleField()
                    .setText(Lang.get("report.settings.chart.generated_title", xLabel, yLabel));
        }
    }

    private interface ColumnFilter {
        boolean run(DataTable table, int columnIndex);
    }

    private String[] getColumnNames(ColumnFilter func, boolean showTypes) {
        DataTable table = getTable();
        if (table == null)
            return new String[0];

        List<String> names = new ArrayList<>();
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (func.run(table, i)) {
                names.add(showTypes
                        ? String.format("(%s) %s", table.getColumnType(i), table.getColumnName(i))
                        : table.getColumnName(i));
            }
        }
        return names.toArray(new String[0]);
    }

    public DataTable getTable() {
        String selected = (String) tables.getSelectedItem();

        if (selected == null || selected.equals(Lang.get("report.settings.data.current_table"))) {
            return GUIController.getInstance().getTable().orElse(null);
        }

        return TableService.get(new File(selected));
    }

    public Optional<Integer> getBiggerGroupColumn() {
        int index = biggerGroupColumn.getSelectedIndex();
        return index <= 0 ? Optional.empty() : Optional.of(index - 1);
    }

    public int getGroupColumn() {
        int idx = groupColumn.getSelectedIndex();
        if (idx < 0) {
            return -1;
        }

        for (int hidden : removedGroupColumns) {
            if (hidden <= idx) {
                idx++;

            } else {
                break;
            }
        }
        return idx;
    }

    public int getValueColumn() {
        int idx = valueColumn.getSelectedIndex();
        if (idx < 0) {
            return -1;
        }

        for (int hidden : removedValueColumns) {
            if (hidden <= idx) {
                idx++;

            } else {
                break;
            }
        }
        return idx;
    }

    public boolean isIncludeFilters() {
        return filterInclude.isSelected();
    }

    public boolean isPercentage() {
        return percentageCheck.isSelected();
    }

    public void setIsPercentage(boolean percentage) {
        percentageCheck.setSelected(percentage);
    }

    public void setIncludeFilters(boolean include) {
        filterInclude.setSelected(include);
    }

    public void setGroupColumn(int index) {
        int removedBefore = (int) removedGroupColumns.stream().filter(r -> r <= index).count();
        int real = index - removedBefore;

        if (real >= 0 && real < groupColumn.getItemCount()) {
            groupColumn.setSelectedIndex(real);
        }
    }

    public void setValueColumn(int index) {
        int removedBefore = (int) removedValueColumns.stream().filter(r -> r <= index).count();
        int real = index - removedBefore;

        if (real >= 0 && real < valueColumn.getItemCount()) {
            valueColumn.setSelectedIndex(real);
        }
    }

    public void setBiggerGroupColumn(Optional<Integer> index) {
        if (index.isPresent() && index.get() + 1 < biggerGroupColumn.getItemCount()) {
            biggerGroupColumn.setSelectedIndex(index.get() + 1);
        } else {
            biggerGroupColumn.setSelectedIndex(0);
        }
    }

    public void setTable(DataTable table) {
        if (table == null)
            return;

        String path = table.getPath().toString();
        if (currentTablePath != null && currentTablePath.equals(table.getPath())) {
            tables.setSelectedIndex(0);
            return;
        }

        for (int i = 0; i < tables.getItemCount(); i++) {
            String item = tables.getItemAt(i);

            if (item.equals(path) ||
                    (item.equals(Lang.get("report.settings.data.current_table"))
                            && GUIController.getInstance().getTable()
                                    .map(t -> t.getPath()).orElse(null)
                                    .equals(table.getPath()))) {
                tables.setSelectedIndex(i);
                break;
            }
        }
    }

    public void refresh() {
        clearRows();
        build();
        revalidate();
        repaint();
    }
}