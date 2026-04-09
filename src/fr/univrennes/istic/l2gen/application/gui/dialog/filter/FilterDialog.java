package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.filter.FilterLogic;
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
    private JComboBox<FilterType> filterTypeComboBox;
    private JComboBox<String> sortDirectionComboBox;
    private JComboBox<String> logicOperatorComboBox;
    private JPanel conditionOptionsPanel;

    private FilterDialog(Frame parent, DataTable table) {
        super(parent, "Filter Builder", true);

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
        panel.add(new JLabel("Column:"), labelConstraints);
        columnComboBox = new JComboBox<>(columnNames.toArray(new String[0]));
        panel.add(columnComboBox, fieldConstraints);

        labelConstraints.gridy = 1;
        fieldConstraints.gridy = 1;
        panel.add(new JLabel("Filter type:"), labelConstraints);
        filterTypeComboBox = new JComboBox<>(FilterType.values());
        panel.add(filterTypeComboBox, fieldConstraints);

        labelConstraints.gridy = 2;
        fieldConstraints.gridy = 2;
        panel.add(new JLabel("Logic operator:"), labelConstraints);
        logicOperatorComboBox = new JComboBox<>(new String[] { "AND", "OR" });
        panel.add(logicOperatorComboBox, fieldConstraints);

        conditionOptionsPanel = new JPanel(new CardLayout());
        conditionOptionsPanel.add(buildSearchPanel(), "SEARCH");
        conditionOptionsPanel.add(buildRangePanel(), "RANGE");
        conditionOptionsPanel.add(buildNPanel("Top N value:"), "TOP_N");
        conditionOptionsPanel.add(buildNPanel("Bottom N value:"), "BOTTOM_N");
        conditionOptionsPanel.add(new JPanel(), "SHOW_EMPTY");
        conditionOptionsPanel.add(new JPanel(), "HIDE_EMPTY");
        conditionOptionsPanel.add(buildSortPanel(), "SORT");

        labelConstraints.gridy = 3;
        labelConstraints.anchor = GridBagConstraints.NORTHWEST;
        fieldConstraints.gridy = 3;
        panel.add(new JLabel("Parameters:"), labelConstraints);
        panel.add(conditionOptionsPanel, fieldConstraints);

        filterTypeComboBox.addActionListener(event -> {
            FilterType selectedType = (FilterType) filterTypeComboBox.getSelectedItem();
            CardLayout cardLayout = (CardLayout) conditionOptionsPanel.getLayout();
            if (selectedType != null) {
                cardLayout.show(conditionOptionsPanel, selectedType.name());
            }
            logicOperatorComboBox.setEnabled(selectedType != FilterType.SORT);
        });

        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 4));
        JButton addFilterButton = new JButton("Add filter");
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
        panel.add(new JLabel("Search term:"), BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildRangePanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 6, 0));
        JTextField minField = new JTextField();
        minField.setName("minField");
        JTextField maxField = new JTextField();
        maxField.setName("maxField");
        panel.add(new JLabel("Min:"));
        panel.add(minField);
        panel.add(new JLabel("Max:"));
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
        sortDirectionComboBox = new JComboBox<>(new String[] { "Ascending", "Descending" });
        panel.add(new JLabel("Direction:"), BorderLayout.WEST);
        panel.add(sortDirectionComboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildConditionsListPanel() {
        conditionsPanel.setLayout(new BoxLayout(conditionsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(conditionsPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                null,
                "Added Filters",
                TitledBorder.LEFT,
                TitledBorder.TOP));
        scrollPane.setPreferredSize(new Dimension(0, 180));

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildButtonBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> {
            result.clear();
            dispose();
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(event -> dispose());

        panel.add(cancelButton);
        panel.add(confirmButton);
        return panel;
    }

    private void addCurrentFilter() {
        FilterType selectedType = (FilterType) filterTypeComboBox.getSelectedItem();
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
                        showValidationError("Please enter a search term.");
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
            showValidationError("Invalid numeric value. Please enter a valid number.");
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

    void removeConditionRow(FilterRow row) {
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
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.WARNING_MESSAGE);
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