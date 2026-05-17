package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.core.filter.FilterCondition;
import fr.univrennes.istic.l2gen.application.core.filter.FilterLogic;
import fr.univrennes.istic.l2gen.application.core.filter.FilterOperator;
import fr.univrennes.istic.l2gen.application.core.services.statistic.StringStatisticService;
import fr.univrennes.istic.l2gen.application.core.table.DataType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class ConditionInputRow {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final FilterOperator[] STRING_FREE_OPERATORS = {
            FilterOperator.EQUAL, FilterOperator.LIKE, FilterOperator.NOT_EQUAL,
            FilterOperator.IS_NULL, FilterOperator.NOT_NULL
    };
    private static final FilterOperator[] STRING_CATEGORICAL_OPERATORS = {
            FilterOperator.EQUAL, FilterOperator.IS_NULL, FilterOperator.NOT_NULL
    };
    private static final FilterOperator[] NUMERIC_OPERATORS = {
            FilterOperator.EQUAL, FilterOperator.NOT_EQUAL,
            FilterOperator.GREATER, FilterOperator.GREATER_EQUAL,
            FilterOperator.LESS, FilterOperator.LESS_EQUAL,
            FilterOperator.IS_NULL, FilterOperator.NOT_NULL
    };
    private static final FilterOperator[] DATE_OPERATORS = {
            FilterOperator.EQUAL, FilterOperator.NOT_EQUAL,
            FilterOperator.GREATER, FilterOperator.GREATER_EQUAL,
            FilterOperator.LESS, FilterOperator.LESS_EQUAL,
            FilterOperator.IS_NULL, FilterOperator.NOT_NULL
    };
    private static final FilterOperator[] BOOLEAN_OPERATORS = {
            FilterOperator.EQUAL, FilterOperator.IS_NULL, FilterOperator.NOT_NULL
    };

    private static final String CARD_TEXT = "TEXT";
    private static final String CARD_CATEGORY = "CATEGORY";
    private static final String CARD_NUMERIC = "NUMERIC";
    private static final String CARD_DATE = "DATE";
    private static final String CARD_BOOLEAN = "BOOLEAN";
    private static final String CARD_NONE = "NONE";

    private final FilterDialog owner;
    private DataType columnType;
    private int realColumnIndex;
    private final boolean showLogicToggle;

    private JPanel panel;
    private JComboBox<String> logicComboBox;
    private JComboBox<FilterOperator> operatorComboBox;
    private JPanel valuePanel;
    private CardLayout valueCardLayout;

    private JTextField textValueField;
    private JComboBox<String> categoryComboBox;
    private JSpinner numericValueSpinner;
    private JSpinner dateValueSpinner;
    private JComboBox<String> booleanValueComboBox;

    public ConditionInputRow(FilterDialog owner, DataType columnType, int realColumnIndex, boolean showLogicToggle) {
        this.owner = owner;
        this.columnType = columnType;
        this.realColumnIndex = realColumnIndex;
        this.showLogicToggle = showLogicToggle;
        buildPanel();
    }

    private void buildPanel() {
        panel = new JPanel(new BorderLayout(4, 0));
        panel.setBorder(new EmptyBorder(3, 6, 3, 6));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JPanel leftSection = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        leftSection.setOpaque(false);

        if (showLogicToggle) {
            logicComboBox = new JComboBox<>(new String[] {
                    Lang.get("filter.logic_operator.and"),
                    Lang.get("filter.logic_operator.or")
            });
            logicComboBox.setPreferredSize(new Dimension(60, 26));
            leftSection.add(logicComboBox);
        } else {
            JLabel placeholderLabel = new JLabel();
            placeholderLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
            placeholderLabel.setPreferredSize(new Dimension(60, 26));
            placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
            leftSection.add(placeholderLabel);
        }

        operatorComboBox = new JComboBox<>(buildOperatorArray(columnType));
        operatorComboBox.setRenderer(new FilterOperatorRenderer());
        operatorComboBox.setPreferredSize(new Dimension(120, 26));
        operatorComboBox.addActionListener(event -> refreshValueVisibility());
        leftSection.add(operatorComboBox);

        valueCardLayout = new CardLayout();
        valuePanel = new JPanel(valueCardLayout);
        valuePanel.setOpaque(false);

        textValueField = new JTextField();
        textValueField.setPreferredSize(new Dimension(120, 26));
        valuePanel.add(textValueField, CARD_TEXT);

        categoryComboBox = new JComboBox<>();
        categoryComboBox.setPreferredSize(new Dimension(120, 26));
        valuePanel.add(categoryComboBox, CARD_CATEGORY);

        numericValueSpinner = FilterDialog.createDoubleSpinner();
        valuePanel.add(numericValueSpinner, CARD_NUMERIC);

        dateValueSpinner = FilterDialog.createDateSpinner();
        valuePanel.add(dateValueSpinner, CARD_DATE);

        booleanValueComboBox = new JComboBox<>(new String[] { "true", "false" });
        valuePanel.add(booleanValueComboBox, CARD_BOOLEAN);

        valuePanel.add(new JLabel(), CARD_NONE);

        JButton removeButton = new JButton(UIManager.getIcon("InternalFrame.closeIcon"));
        removeButton.setPreferredSize(new Dimension(26, 26));
        removeButton.setToolTipText(Lang.get("filter.remove_condition"));
        removeButton.addActionListener(event -> owner.removeConditionInputRow(this));

        panel.add(leftSection, BorderLayout.WEST);
        panel.add(valuePanel, BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.EAST);

        refreshOperatorsAndValues();
    }

    private FilterOperator[] buildOperatorArray(DataType type) {
        return switch (type) {
            case STRING -> isCurrentColumnCategorical() ? STRING_CATEGORICAL_OPERATORS : STRING_FREE_OPERATORS;
            case INTEGER, DOUBLE -> NUMERIC_OPERATORS;
            case DATE -> DATE_OPERATORS;
            case BOOLEAN -> BOOLEAN_OPERATORS;
            case EMPTY -> new FilterOperator[] { FilterOperator.IS_NULL, FilterOperator.NOT_NULL };
        };
    }

    private boolean isCurrentColumnCategorical() {
        if (columnType != DataType.STRING || realColumnIndex < 0) {
            return false;
        }
        return StringStatisticService.hasCategories(owner.getTable(), realColumnIndex);
    }

    private void refreshOperatorsAndValues() {
        FilterOperator previouslySelected = (FilterOperator) operatorComboBox.getSelectedItem();
        operatorComboBox.setModel(new DefaultComboBoxModel<>(buildOperatorArray(columnType)));

        if (previouslySelected != null) {
            for (int i = 0; i < operatorComboBox.getItemCount(); i++) {
                if (operatorComboBox.getItemAt(i) == previouslySelected) {
                    operatorComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        if (columnType == DataType.STRING && isCurrentColumnCategorical()) {
            List<String> categories = StringStatisticService.getCategories(owner.getTable(), realColumnIndex);
            categoryComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
        }

        refreshValueVisibility();
    }

    private void refreshValueVisibility() {
        FilterOperator selectedOperator = (FilterOperator) operatorComboBox.getSelectedItem();
        if (selectedOperator == null) {
            return;
        }

        if (selectedOperator == FilterOperator.IS_NULL || selectedOperator == FilterOperator.NOT_NULL) {
            valueCardLayout.show(valuePanel, CARD_NONE);
            return;
        }

        String card = switch (columnType) {
            case INTEGER, DOUBLE -> CARD_NUMERIC;
            case DATE -> CARD_DATE;
            case BOOLEAN -> CARD_BOOLEAN;
            case STRING -> isCurrentColumnCategorical() ? CARD_CATEGORY : CARD_TEXT;
            default -> CARD_NONE;
        };

        valueCardLayout.show(valuePanel, card);
    }

    public void updateColumnType(DataType newType, int newRealColumnIndex) {
        this.columnType = newType;
        this.realColumnIndex = newRealColumnIndex;
        refreshOperatorsAndValues();
    }

    public void loadCondition(FilterCondition condition) {
        FilterOperator operator = condition.operator();
        for (int i = 0; i < operatorComboBox.getItemCount(); i++) {
            if (operatorComboBox.getItemAt(i) == operator) {
                operatorComboBox.setSelectedIndex(i);
                break;
            }
        }

        String value = condition.value();
        if (value == null || value.isEmpty()) {
            refreshValueVisibility();
            return;
        }

        switch (columnType) {
            case INTEGER, DOUBLE -> {
                try {
                    numericValueSpinner.setValue(Double.parseDouble(value));
                } catch (NumberFormatException ignored) {
                }
            }
            case DATE -> {
                try {
                    LocalDateTime ldt = LocalDateTime.parse(value, TIMESTAMP_FORMATTER);
                    dateValueSpinner.setValue(Timestamp.valueOf(ldt));
                } catch (Exception ignored) {
                }
            }
            case BOOLEAN -> booleanValueComboBox.setSelectedItem(value);
            case STRING -> {
                if (isCurrentColumnCategorical()) {
                    categoryComboBox.setSelectedItem(value);
                } else {
                    textValueField.setText(value);
                }
            }
            default -> textValueField.setText(value);
        }

        refreshValueVisibility();
    }

    public FilterCondition buildCondition() throws ParseException {
        FilterOperator selectedOperator = (FilterOperator) operatorComboBox.getSelectedItem();
        if (selectedOperator == null) {
            return null;
        }

        if (selectedOperator == FilterOperator.IS_NULL || selectedOperator == FilterOperator.NOT_NULL) {
            return new FilterCondition(selectedOperator);
        }

        String value = readCurrentValue();
        if (value == null) {
            return null;
        }

        return new FilterCondition(selectedOperator, value);
    }

    private String readCurrentValue() throws ParseException {
        return switch (columnType) {
            case INTEGER -> String.valueOf(FilterDialog.readIntegerSpinner(numericValueSpinner));
            case DOUBLE -> String.valueOf(FilterDialog.readDoubleSpinner(numericValueSpinner));
            case DATE -> {
                Timestamp timestamp = FilterDialog.readTimestampSpinner(dateValueSpinner);
                yield timestamp.toLocalDateTime().format(TIMESTAMP_FORMATTER);
            }
            case BOOLEAN -> (String) booleanValueComboBox.getSelectedItem();
            case STRING -> {
                if (isCurrentColumnCategorical()) {
                    yield (String) categoryComboBox.getSelectedItem();
                }
                String text = textValueField.getText().trim();
                yield text.isEmpty() ? null : text;
            }
            default -> null;
        };
    }

    public FilterLogic getSelectedLogic() {
        if (logicComboBox == null) {
            return FilterLogic.AND;
        }
        return logicComboBox.getSelectedIndex() == 0 ? FilterLogic.AND : FilterLogic.OR;
    }

    public JPanel getPanel() {
        return panel;
    }
}