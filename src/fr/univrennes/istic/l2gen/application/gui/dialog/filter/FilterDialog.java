package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.filter.FilterLogic;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class FilterDialog extends JDialog {

    private final List<String> columnNames;

    private final List<Filter> result = new ArrayList<>();
    private final JPanel conditionsPanel = new JPanel();
    private final List<FilterRow> conditionRows = new ArrayList<>();

    private JComboBox<String> columnComboBox;
    private JComboBox<String> filterTypeComboBox;
    private JComboBox<String> sortDirectionComboBox;
    private JComboBox<String> logicOperatorComboBox;
    private JPanel conditionOptionsPanel;

    private FilterDialog(Frame parent, DataTable table) {
        super(parent, Lang.get("filter.title"), true);

        this.columnNames = table.getColumnNames();
        build();
        pack();
        setMinimumSize(new Dimension(520, 480));
        setLocationRelativeTo(parent);
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(0, 8));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));

        root.add(buildFilterConfigPanel(), BorderLayout.NORTH);
        root.add(buildConditionsListPanel(), BorderLayout.CENTER);
        root.add(buildButtonBar(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    private JPanel buildFilterConfigPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(8, 8, 8, 8));

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(4, 0, 4, 10);
        labelConstraints.gridx = 0;

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;
        fieldConstraints.insets = new Insets(4, 0, 4, 0);
        fieldConstraints.gridx = 1;

        labelConstraints.gridy = 0;
        fieldConstraints.gridy = 0;
        panel.add(new JLabel(Lang.get("filter.column")), labelConstraints);
        columnComboBox = new JComboBox<>(columnNames.toArray(new String[0]));
        panel.add(columnComboBox, fieldConstraints);

        labelConstraints.gridy = 1;
        fieldConstraints.gridy = 1;
        panel.add(new JLabel(Lang.get("filter.type")), labelConstraints);

        String[] types = new String[FilterType.values().length];
        for (int i = 0; i < FilterType.values().length; i++) {
            types[i] = Lang.get("filter.type." + FilterType.values()[i].name().toLowerCase());
        }
        filterTypeComboBox = new JComboBox<>(types);
        panel.add(filterTypeComboBox, fieldConstraints);

        labelConstraints.gridy = 2;
        fieldConstraints.gridy = 2;
        panel.add(new JLabel(Lang.get("filter.logic_operator")), labelConstraints);
        logicOperatorComboBox = new JComboBox<>(
                new String[] { Lang.get("filter.logic_operator.and"), Lang.get("filter.logic_operator.or") });
        panel.add(logicOperatorComboBox, fieldConstraints);

        conditionOptionsPanel = new JPanel(new CardLayout());
        conditionOptionsPanel.add(buildSearchPanel(), Lang.get("filter.type.search"));
        conditionOptionsPanel.add(buildRangePanel(), Lang.get("filter.type.range"));
        conditionOptionsPanel.add(buildNPanel(Lang.get("filter.type.top_n")), Lang.get("filter.type.top_n"));
        conditionOptionsPanel.add(buildNPanel(Lang.get("filter.type.bottom_n")), Lang.get("filter.type.bottom_n"));
        conditionOptionsPanel.add(new JPanel(), Lang.get("filter.type.show_empty"));
        conditionOptionsPanel.add(new JPanel(), Lang.get("filter.type.hide_empty"));
        conditionOptionsPanel.add(buildSortPanel(), Lang.get("filter.type.sort"));

        labelConstraints.gridy = 3;
        labelConstraints.anchor = GridBagConstraints.NORTHWEST;
        fieldConstraints.gridy = 3;
        panel.add(new JLabel(Lang.get("filter.parameters")), labelConstraints);
        panel.add(conditionOptionsPanel, fieldConstraints);

        filterTypeComboBox.addActionListener(event -> {
            String selectedTypeName = (String) filterTypeComboBox.getSelectedItem();
            FilterType selectedType = null;
            for (FilterType type : FilterType.values()) {
                if (Lang.get("filter.type." + type.name().toLowerCase()).equals(selectedTypeName)) {
                    selectedType = type;
                    break;
                }
            }

            CardLayout cardLayout = (CardLayout) conditionOptionsPanel.getLayout();
            if (selectedType != null) {
                cardLayout.show(conditionOptionsPanel, Lang.get("filter.type." + selectedType.name().toLowerCase()));
            }
            logicOperatorComboBox.setEnabled(selectedType != FilterType.SORT);
        });

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        JButton addFilterButton = new JButton(Lang.get("filter.add"));
        addFilterButton.addActionListener(event -> addCurrentFilter());
        addButtonPanel.add(addFilterButton);

        GridBagConstraints fullRowConstraints = new GridBagConstraints();
        fullRowConstraints.gridx = 0;
        fullRowConstraints.gridy = 4;
        fullRowConstraints.gridwidth = 2;
        fullRowConstraints.fill = GridBagConstraints.HORIZONTAL;
        fullRowConstraints.insets = new Insets(4, 0, 0, 0);
        panel.add(addButtonPanel, fullRowConstraints);

        return panel;
    }

    private JPanel buildSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 0));
        JTextField searchField = new JTextField();
        searchField.setName("searchField");
        panel.add(new JLabel(Lang.get("filter.search_term")), BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildRangePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 6, 0));
        JTextField minField = new JTextField();
        minField.setName("minField");
        JTextField maxField = new JTextField();
        maxField.setName("maxField");
        panel.add(new JLabel(Lang.get("filter.min")));
        panel.add(minField);
        panel.add(new JLabel(Lang.get("filter.max")));
        panel.add(maxField);
        return panel;
    }

    private JPanel buildNPanel(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(6, 0));
        JTextField nField = new JTextField();
        nField.setName("nField");
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(nField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildSortPanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 0));
        sortDirectionComboBox = new JComboBox<>(
                new String[] { Lang.get("filter.sort.ascending"), Lang.get("filter.sort.descending") });
        panel.add(new JLabel(Lang.get("filter.sort.direction")), BorderLayout.WEST);
        panel.add(sortDirectionComboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildConditionsListPanel() {
        conditionsPanel.setLayout(new BoxLayout(conditionsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(conditionsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                null,
                Lang.get("filter.added_conditions"),
                TitledBorder.LEFT,
                TitledBorder.TOP));
        scrollPane.setPreferredSize(new Dimension(0, 180));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildButtonBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton cancelButton = new JButton(Lang.get("filter.cancel"));
        cancelButton.addActionListener(event -> {
            result.clear();
            dispose();
        });

        JButton confirmButton = new JButton(Lang.get("filter.confirm"));
        confirmButton.addActionListener(event -> dispose());

        panel.add(cancelButton);
        panel.add(confirmButton);
        return panel;
    }

    private void addCurrentFilter() {
        String selectedTypeName = (String) filterTypeComboBox.getSelectedItem();
        FilterType selectedType = null;
        for (FilterType type : FilterType.values()) {
            if (Lang.get("filter.type." + type.name().toLowerCase()).equals(selectedTypeName)) {
                selectedType = type;
                break;
            }
        }

        int selectedColumnIndex = columnComboBox.getSelectedIndex();
        String selectedColumnName = columnNames.get(selectedColumnIndex);
        FilterLogic selectedLogic = logicOperatorComboBox.getSelectedIndex() == 0 ? FilterLogic.AND : FilterLogic.OR;

        if (selectedType == null)
            return;

        Filter filter = null;
        String description = null;

        try {
            switch (selectedType) {
                case SEARCH -> {
                    JTextField searchField = findTextField(conditionOptionsPanel.getComponent(0), "searchField");
                    String searchTerm = searchField != null ? searchField.getText().trim() : "";
                    if (searchTerm.isEmpty()) {
                        showValidationError(Lang.get("filter.validation.search_term"));
                        return;
                    }
                    filter = Filter.search(selectedColumnIndex, searchTerm);
                    description = selectedColumnName + " LIKE \"" + searchTerm + "\"";
                }
                case RANGE -> {
                    JTextField minField = findTextField(conditionOptionsPanel.getComponent(1), "minField");
                    JTextField maxField = findTextField(conditionOptionsPanel.getComponent(1), "maxField");
                    double minValue = Double.parseDouble(minField != null ? minField.getText().trim() : "");
                    double maxValue = Double.parseDouble(maxField != null ? maxField.getText().trim() : "");
                    filter = Filter.byRange(selectedColumnIndex, minValue, maxValue);
                    description = selectedColumnName + " BETWEEN " + minValue + " AND " + maxValue;
                }
                case TOP_N -> {
                    JTextField nField = findTextField(conditionOptionsPanel.getComponent(2), "nField");
                    int topNValue = Integer.parseInt(nField != null ? nField.getText().trim() : "");
                    filter = Filter.topN(selectedColumnIndex, topNValue);
                    description = selectedColumnName + " > " + topNValue;
                }
                case BOTTOM_N -> {
                    JTextField nField = findTextField(conditionOptionsPanel.getComponent(3), "nField");
                    int bottomNValue = Integer.parseInt(nField != null ? nField.getText().trim() : "");
                    filter = Filter.bottomN(selectedColumnIndex, bottomNValue);
                    description = selectedColumnName + " < " + bottomNValue;
                }
                case SHOW_EMPTY -> {
                    filter = Filter.showEmpty(selectedColumnIndex);
                    description = selectedColumnName + " IS NULL";
                }
                case HIDE_EMPTY -> {
                    filter = Filter.hideEmpty(selectedColumnIndex);
                    description = selectedColumnName + " IS NOT NULL";
                }
                case SORT -> {
                    boolean ascending = sortDirectionComboBox.getSelectedIndex() == 0;
                    filter = Filter.sort(selectedColumnIndex, ascending);
                    description = "ORDER BY " + selectedColumnName + (ascending ? " ASC" : " DESC");
                }
            }
        } catch (NumberFormatException ex) {
            showValidationError(Lang.get("filter.validation.invalid_number"));
            return;
        }

        if (filter == null)
            return;

        if (selectedType != FilterType.SORT) {
            filter.setOperator(selectedLogic);
        }

        result.add(filter);
        addConditionRow(description, filter);
    }

    private void addConditionRow(String description, Filter filter) {
        FilterRow row = new FilterRow(this, description, filter);
        conditionRows.add(row);
        conditionsPanel.add(row.getPanel());
        conditionsPanel.revalidate();
        conditionsPanel.repaint();
    }

    public void removeConditionRow(FilterRow row) {
        result.remove(row.getFilter());
        conditionRows.remove(row);
        conditionsPanel.remove(row.getPanel());
        conditionsPanel.revalidate();
        conditionsPanel.repaint();
    }

    private JTextField findTextField(Component parent, String name) {
        if (parent instanceof JTextField textField && name.equals(textField.getName())) {
            return textField;
        }
        if (parent instanceof Container container) {
            for (Component child : container.getComponents()) {
                JTextField found = findTextField(child, name);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

    private void showValidationError(String message) {
        JOptionPane.showMessageDialog(this, message, Lang.get("filter.validation.error"), JOptionPane.WARNING_MESSAGE);
    }

    public List<Filter> getResult() {
        return result;
    }

    public static List<Filter> show(Frame parent, DataTable table) {
        FilterDialog dialog = new FilterDialog(parent, table);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}