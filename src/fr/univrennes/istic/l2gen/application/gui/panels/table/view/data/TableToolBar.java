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

        TableToolBar(TablePanel tablePanel) {
                setFloatable(false);

                JButton advancedFilterButton = new JButton(Lang.get("tabletoolbar.filters"),
                                Ico.get("icons/filter_on.svg"));
                advancedFilterButton.addActionListener(e -> GUIController.getInstance().onOpenFilterDialog());

                JButton clearFiltersButton = new JButton(Lang.get("tabletoolbar.clear_filters"),
                                Ico.get("icons/filter_off.svg"));
                clearFiltersButton.addActionListener(e -> GUIController.getInstance().onFilterReset());

                JButton showAllColumnsButton = new JButton(Lang.get("tabletoolbar.show_all_columns"),
                                Ico.get("icons/show.svg"));
                showAllColumnsButton.addActionListener(e -> tablePanel.getTable().showAllColumns());

                JButton hideEmptyColumnsButton = new JButton(Lang.get("tabletoolbar.hide_empty_columns"),
                                Ico.get("icons/hide.svg"));
                hideEmptyColumnsButton.addActionListener(e -> tablePanel.getTable().hideEmptyColumns());

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
}