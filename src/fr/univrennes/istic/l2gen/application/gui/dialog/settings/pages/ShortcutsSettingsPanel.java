package fr.univrennes.istic.l2gen.application.gui.dialog.settings.pages;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsPage;
import fr.univrennes.istic.l2gen.application.gui.shortcuts.Shortcuts;

public final class ShortcutsSettingsPanel extends JPanel implements SettingsPage {

    private static final int COLUMN_SHORTCUT = 2;

    private final List<ShortcutEntry> shortcuts;
    private final DefaultTableModel tableModel;

    public ShortcutsSettingsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(new EmptyBorder(16, 16, 16, 16));

        String[] columnHeaders = {
                Lang.get("settings.shortcuts.column.category"),
                Lang.get("settings.shortcuts.column.action"),
                Lang.get("settings.shortcuts.column.shortcut")
        };

        shortcuts = buildShortcuts();
        tableModel = new DefaultTableModel(buildShortcutData(), columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable shortcutsTable = new JTable(tableModel);
        shortcutsTable.setFont(shortcutsTable.getFont().deriveFont(Font.PLAIN, 12f));
        shortcutsTable.setRowHeight(26);
        shortcutsTable.setShowVerticalLines(false);
        shortcutsTable.setShowHorizontalLines(true);
        shortcutsTable.setGridColor(UIManager.getColor("Separator.foreground"));
        shortcutsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shortcutsTable.getTableHeader().setFont(shortcutsTable.getFont().deriveFont(Font.BOLD, 11f));
        shortcutsTable.getTableHeader().setReorderingAllowed(false);

        shortcutsTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        shortcutsTable.getColumnModel().getColumn(1).setPreferredWidth(280);
        shortcutsTable.getColumnModel().getColumn(2).setPreferredWidth(120);

        DefaultTableCellRenderer shortcutCellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 2 && !isSelected) {
                    setFont(getFont().deriveFont(Font.BOLD, 12f));
                    setForeground(UIManager.getColor("Label.disabledForeground"));
                    setBorder(BorderFactory.createCompoundBorder(
                            new EmptyBorder(2, 4, 2, 4),
                            BorderFactory.createLineBorder(
                                    UIManager.getColor("Separator.foreground"), 1)));
                } else if (!isSelected) {
                    setForeground(UIManager.getColor("Table.foreground"));
                    setBorder(new EmptyBorder(2, 4, 2, 4));
                }
                return this;
            }
        };
        shortcutsTable.getColumnModel().getColumn(2).setCellRenderer(shortcutCellRenderer);

        shortcutsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!SwingUtilities.isLeftMouseButton(e) || e.getClickCount() != 1) {
                    return;
                }

                int row = shortcutsTable.rowAtPoint(e.getPoint());
                int column = shortcutsTable.columnAtPoint(e.getPoint());
                if (row < 0 || column != COLUMN_SHORTCUT) {
                    return;
                }

                shortcutsTable.setRowSelectionInterval(row, row);
                String currentValue = String.valueOf(tableModel.getValueAt(row, COLUMN_SHORTCUT));
                String updatedValue = showShortcutDialog(currentValue);
                if (updatedValue != null) {
                    tableModel.setValueAt(updatedValue, row, COLUMN_SHORTCUT);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(shortcutsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), 1));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 400));

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public boolean applySettings() {
        boolean changed = false;
        for (int row = 0; row < shortcuts.size(); row++) {
            ShortcutEntry entry = shortcuts.get(row);
            String currentValue = Shortcuts.getShortcut(entry.configKey(), entry.defaultValue());

            Object rawValue = tableModel.getValueAt(row, COLUMN_SHORTCUT);
            String newValue = rawValue == null ? "" : rawValue.toString().trim();

            if (currentValue.equals(newValue)) {
                continue;
            }

            if (!Shortcuts.isValidShortcut(newValue)) {
                tableModel.setValueAt(currentValue, row, COLUMN_SHORTCUT);
                continue;
            }

            Config.put(entry.configKey(), newValue);
            changed = true;
        }

        return changed;
    }

    @Override
    public boolean requiresRestart() {
        return true;
    }

    private List<ShortcutEntry> buildShortcuts() {
        List<ShortcutEntry> entries = new ArrayList<>();

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.table"),
                Lang.get("settings.shortcuts.table.open"),
                Shortcuts.KEY_TABLE_OPEN,
                Shortcuts.DEFAULT_TABLE_OPEN));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.table"),
                Lang.get("settings.shortcuts.table.close"),
                Shortcuts.KEY_TABLE_CLOSE,
                Shortcuts.DEFAULT_TABLE_CLOSE));

        entries.add(new ShortcutEntry(
                Lang.get("menu.file"),
                Lang.get("menu.file.open_url"),
                Shortcuts.KEY_FILE_OPEN_URL,
                Shortcuts.DEFAULT_FILE_OPEN_URL));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.view"),
                Lang.get("settings.shortcuts.view.filter"),
                Shortcuts.KEY_VIEW_FILTER,
                Shortcuts.DEFAULT_VIEW_FILTER));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.view"),
                Lang.get("settings.shortcuts.view.settings"),
                Shortcuts.KEY_VIEW_SETTINGS,
                Shortcuts.DEFAULT_VIEW_SETTINGS));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.sort"),
                Lang.get("settings.shortcuts.sort.sort_asc"),
                Shortcuts.KEY_SORT_ASC,
                Shortcuts.DEFAULT_SORT_ASC));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.sort"),
                Lang.get("settings.shortcuts.sort.sort_desc"),
                Shortcuts.KEY_SORT_DESC,
                Shortcuts.DEFAULT_SORT_DESC));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.undo"),
                Lang.get("settings.shortcuts.notebook.undo"),
                Shortcuts.KEY_NOTEBOOK_UNDO,
                Shortcuts.DEFAULT_NOTEBOOK_UNDO));

        entries.add(new ShortcutEntry(
                Lang.get("settings.shortcuts.redo"),
                Lang.get("settings.shortcuts.notebook.redo"),
                Shortcuts.KEY_NOTEBOOK_REDO,
                Shortcuts.DEFAULT_NOTEBOOK_REDO));

        entries.add(new ShortcutEntry(
                Lang.get("menu.file"),
                Lang.get("menu.file.exit"),
                Shortcuts.KEY_FILE_EXIT,
                Shortcuts.DEFAULT_FILE_EXIT));

        entries.add(new ShortcutEntry(
                Lang.get("menu.help"),
                Lang.get("menu.help.documentation"),
                Shortcuts.KEY_HELP_DOCUMENTATION,
                Shortcuts.DEFAULT_HELP_DOCUMENTATION));

        return entries;
    }

    private Object[][] buildShortcutData() {
        Object[][] data = new Object[shortcuts.size()][3];
        for (int i = 0; i < shortcuts.size(); i++) {
            ShortcutEntry entry = shortcuts.get(i);
            data[i][0] = entry.categoryLabel();
            data[i][1] = entry.actionLabel();
            data[i][2] = Shortcuts.getShortcut(entry.configKey(), entry.defaultValue());
        }
        return data;
    }

    private String showShortcutDialog(String currentValue) {
        Window owner = SwingUtilities.getWindowAncestor(this);
        ShortcutCaptureDialog dialog = new ShortcutCaptureDialog(owner, currentValue);
        return dialog.showDialog();
    }

    private record ShortcutEntry(String categoryLabel, String actionLabel, String configKey, String defaultValue) {
    }

    private static final class ShortcutCaptureDialog extends JDialog {
        private final JPanel capturePanel = new JPanel(new BorderLayout());
        private final JLabel valueLabel = new JLabel("");
        private final JButton applyButton = new JButton(Lang.get("settings.footer.apply"));
        private String capturedValue;
        private boolean confirmed;

        private ShortcutCaptureDialog(Window owner, String currentValue) {
            super(owner, Lang.get("settings.shortcuts.column.shortcut"), ModalityType.APPLICATION_MODAL);

            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setResizable(false);

            JPanel root = new JPanel(new BorderLayout());
            root.setBorder(new EmptyBorder(16, 16, 16, 16));
            root.setBackground(UIManager.getColor("Panel.background"));

            JLabel hintLabel = new JLabel(Lang.get("settings.shortcuts.capture.hint"));
            hintLabel.setFont(hintLabel.getFont().deriveFont(Font.PLAIN, 12f));
            hintLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
            root.add(hintLabel, BorderLayout.NORTH);

            capturePanel.setOpaque(false);
            capturePanel.setBorder(new EmptyBorder(12, 0, 12, 0));
            capturePanel.setFocusable(true);

            valueLabel.setFont(valueLabel.getFont().deriveFont(Font.BOLD, 16f));
            valueLabel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), 1),
                    new EmptyBorder(8, 10, 8, 10)));
            valueLabel.setText(currentValue == null || currentValue.isBlank() ? "-" : currentValue);
            capturePanel.add(valueLabel, BorderLayout.CENTER);
            root.add(capturePanel, BorderLayout.CENTER);

            JPanel actions = new JPanel();
            actions.setOpaque(false);

            JButton cancelButton = new JButton(Lang.get("settings.footer.cancel"));
            cancelButton.addActionListener(event -> dispose());

            applyButton.setEnabled(false);
            applyButton.addActionListener(event -> {
                confirmed = true;
                dispose();
            });

            actions.add(cancelButton);
            actions.add(Box.createHorizontalStrut(8));
            actions.add(applyButton);

            root.add(actions, BorderLayout.SOUTH);
            setContentPane(root);
            pack();
            setLocationRelativeTo(owner);

            capturePanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (isModifierKey(e.getKeyCode())) {
                        return;
                    }

                    capturedValue = Shortcuts.formatKeyStroke(
                            KeyStroke.getKeyStroke(e.getKeyCode(), e.getModifiersEx()));
                    if (capturedValue == null || capturedValue.isBlank()) {
                        return;
                    }

                    valueLabel.setText(capturedValue);
                    applyButton.setEnabled(true);
                }
            });

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    SwingUtilities.invokeLater(() -> capturePanel.requestFocusInWindow());
                }
            });
        }

        private static boolean isModifierKey(int keyCode) {
            return keyCode == KeyEvent.VK_SHIFT
                    || keyCode == KeyEvent.VK_CONTROL
                    || keyCode == KeyEvent.VK_ALT
                    || keyCode == KeyEvent.VK_META
                    || keyCode == KeyEvent.VK_ALT_GRAPH;
        }

        private String showDialog() {
            setVisible(true);
            return confirmed ? capturedValue : null;
        }
    }
}
