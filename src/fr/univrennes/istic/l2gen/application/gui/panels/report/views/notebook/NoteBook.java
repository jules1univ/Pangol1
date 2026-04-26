package fr.univrennes.istic.l2gen.application.gui.panels.report.views.notebook;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;
import fr.univrennes.istic.l2gen.application.core.services.NoteBookService;
import fr.univrennes.istic.l2gen.application.gui.GUIController;

public class NoteBook extends JPanel {

    private final DefaultListModel<NoteBookValue> model = new DefaultListModel<>();
    private final JList<NoteBookValue> list = new JList<>(model);

    private final JPopupMenu menu = new JPopupMenu();
    private final JMenuItem moveUpItem = new JMenuItem(Lang.get("report.menu.move_up"));
    private final JMenuItem moveDownItem = new JMenuItem(Lang.get("report.menu.move_down"));
    private final JMenuItem editItem = new JMenuItem(Lang.get("report.menu.edit"));
    private final JMenuItem deleteItem = new JMenuItem(Lang.get("report.menu.delete"));

    public NoteBook() {
        build();
    }

    private void build() {
        setLayout(new BorderLayout());

        NoteBookCellRenderer cellRenderer = new NoteBookCellRenderer(this);
        list.setCellRenderer(cellRenderer);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFixedCellHeight(-1);
        list.setVisibleRowCount(-1);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setDragEnabled(true);
        list.setDropMode(DropMode.INSERT);
        list.setTransferHandler(new NoteBookDragDrop(list, model));

        buildContextMenu();

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showContextMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showContextMenu(e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        NoteBookValue value = model.get(index);
                        SwingUtilities.invokeLater(
                                () -> {
                                    GUIController.getInstance().getMainView().getReportPanel().getSettingView()
                                            .editNoteBookValue(value, index);
                                });
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(null);

        add(scroll, BorderLayout.CENTER);

        refresh();
    }

    private void buildContextMenu() {
        moveUpItem.addActionListener(event -> {
            int index = list.getSelectedIndex();
            if (index > 0) {
                NoteBookService.move(index, index - 1);
                refresh();
                list.setSelectedIndex(index - 1);
            }
        });
        menu.add(moveUpItem);
        moveDownItem.addActionListener(event -> {
            int index = list.getSelectedIndex();
            if (index >= 0 && index < model.size() - 1) {
                NoteBookService.move(index, index + 1);
                refresh();
                list.setSelectedIndex(index + 1);
            }
        });
        menu.add(moveDownItem);

        menu.addSeparator();

        editItem.addActionListener(event -> {
            int index = list.getSelectedIndex();
            if (index >= 0) {
                NoteBookValue value = model.get(index);
                GUIController.getInstance().getMainView().getReportPanel().getSettingView()
                        .editNoteBookValue(value, index);
            }
        });

        menu.add(editItem);
        menu.addSeparator();

        deleteItem.addActionListener(event -> deleteSelectedValue());
        menu.add(deleteItem);
    }

    private void showContextMenu(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            return;
        }

        int index = list.locationToIndex(e.getPoint());
        if (index < 0) {
            return;
        }

        list.setSelectedIndex(index);
        menu.show(list, e.getX(), e.getY());
    }

    private void deleteSelectedValue() {
        int index = list.getSelectedIndex();
        if (index < 0) {
            return;
        }

        model.remove(index);
        NoteBookService.remove(index);
    }

    public void refresh() {
        model.clear();
        for (NoteBookValue value : NoteBookService.getValues()) {
            model.addElement(value);
        }
    }

}