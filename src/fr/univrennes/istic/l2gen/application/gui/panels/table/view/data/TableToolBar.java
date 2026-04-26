package fr.univrennes.istic.l2gen.application.gui.panels.table.view.data;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import fr.univrennes.istic.l2gen.application.core.icon.Ico;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.GUIController;
import fr.univrennes.istic.l2gen.application.gui.panels.table.TablePanel;

public final class TableToolBar extends JToolBar {

        private JButton advancedFilterButton;
        private JButton clearFiltersButton;
        private JButton showAllColumnsButton;
        private JButton hideEmptyColumnsButton;

        private final TablePanel tablePanel;
        private final TableDataView tableView;

        public TableToolBar(TablePanel tablePanel, TableDataView tableView) {
                this.tablePanel = tablePanel;
                this.tableView = tableView;
                build();
        }

        private void build() {
                setFloatable(false);

                advancedFilterButton = new JButton(Lang.get("tabletoolbar.filters"),
                                Ico.get("icons/filter_on.svg"));
                advancedFilterButton.addActionListener(e -> GUIController.getInstance().onOpenFilterDialog());

                clearFiltersButton = new JButton(Lang.get("tabletoolbar.clear_filters"),
                                Ico.get("icons/filter_off.svg"));
                clearFiltersButton.addActionListener(e -> {
                        GUIController.getInstance().getTable().ifPresent(table -> {
                                table.clearFilters();
                                tablePanel.refresh();
                        });
                });

                showAllColumnsButton = new JButton(Lang.get("tabletoolbar.show_all_columns"),
                                Ico.get("icons/show.svg"));
                showAllColumnsButton.addActionListener(e -> {
                        tableView.showAllColumns();
                        showAllColumnsButton.setVisible(false);
                        hideEmptyColumnsButton.setVisible(true);
                });
                showAllColumnsButton.setVisible(tableView.hasHiddenColumns());

                hideEmptyColumnsButton = new JButton(Lang.get("tabletoolbar.hide_empty_columns"),
                                Ico.get("icons/hide.svg"));
                hideEmptyColumnsButton.addActionListener(e -> {
                        tableView.hideEmptyColumns();
                        showAllColumnsButton.setVisible(true);
                        hideEmptyColumnsButton.setVisible(false);
                });
                hideEmptyColumnsButton.setVisible(!tableView.hasHiddenColumns());

                Icon closeIcon = UIManager.getIcon("InternalFrame.closeIcon");
                JButton closeButton = new JButton(closeIcon);
                closeButton.addActionListener(e -> GUIController.getInstance().onCloseTable());

                add(advancedFilterButton);
                add(clearFiltersButton);
                addSeparator();
                add(showAllColumnsButton);
                add(hideEmptyColumnsButton);
                add(Box.createHorizontalGlue());
                add(closeButton);
        }

        public void refresh() {
                showAllColumnsButton.setVisible(tableView.hasHiddenColumns());
                hideEmptyColumnsButton.setVisible(!tableView.hasHiddenColumns());
        }
}