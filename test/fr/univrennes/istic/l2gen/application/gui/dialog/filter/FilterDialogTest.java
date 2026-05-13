package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.core.table.DataType;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests de comportement pour FilterDialog
 */
public class FilterDialogTest {

    @Test
    public void testBuildPanelsAndBuildFilterConfigPanel() throws Exception {
        FilterDialog dialog = newDialogShell(fakeTable(DataType.BOOLEAN));

        JPanel labeled = invokePanel(dialog, "buildLabeledInputPanel",
                new Class<?>[] { String.class, JComponent.class },
                new Object[] { "Label", new JTextField() });
        assertEquals(2, labeled.getComponentCount());

        JPanel range = invokePanel(dialog, "buildRangeInputPanel",
                new Class<?>[] { JComponent.class, JComponent.class },
                new Object[] { new JTextField(), new JTextField() });
        assertEquals(4, range.getComponentCount());

        JPanel sort = invokePanel(dialog, "buildSortPanel");
        assertEquals(2, sort.getComponentCount());

        JPanel conditions = invokePanel(dialog, "buildConditionsListPanel");
        assertEquals(1, conditions.getComponentCount());

        JPanel footer = invokePanel(dialog, "buildButtonBar");
        assertEquals(2, footer.getComponentCount());

        JPanel config = invokePanel(dialog, "buildFilterConfigPanel");
        assertNotNull(config);
        assertNotNull(getFieldTyped(dialog, "columnComboBox", JComboBox.class));
        assertNotNull(getFieldTyped(dialog, "filterTypeComboBox", JComboBox.class));
        assertNotNull(getFieldTyped(dialog, "logicOperatorComboBox", JComboBox.class));
        assertNotNull(getFieldTyped(dialog, "conditionOptionsPanel", JPanel.class));
        assertNotNull(getFieldTyped(dialog, "searchInputsPanel", JPanel.class));
        assertNotNull(getFieldTyped(dialog, "rangeInputsPanel", JPanel.class));
        assertNotNull(getFieldTyped(dialog, "topNInputsPanel", JPanel.class));
        assertNotNull(getFieldTyped(dialog, "bottomNInputsPanel", JPanel.class));
        assertNotNull(getFieldTyped(dialog, "sortDirectionComboBox", JComboBox.class));
        assertTrue(getFieldTyped(dialog, "logicOperatorComboBox", JComboBox.class).isEnabled());
    }

    @Test
    public void testCardsBoundsAndSpinners() throws Exception {
        FilterDialog dialog = newDialogShell(fakeTable(DataType.BOOLEAN));

        setFieldValue(dialog, "searchNumericSpinner", invokeSpinner(dialog, "createDoubleSpinner"));
        setFieldValue(dialog, "rangeNumericMinSpinner", invokeSpinner(dialog, "createDoubleSpinner"));
        setFieldValue(dialog, "rangeNumericMaxSpinner", invokeSpinner(dialog, "createDoubleSpinner"));
        setFieldValue(dialog, "topNNumericSpinner", invokeSpinner(dialog, "createDoubleSpinner"));
        setFieldValue(dialog, "bottomNNumericSpinner", invokeSpinner(dialog, "createDoubleSpinner"));

        setFieldValue(dialog, "rangeDateMinSpinner", invokeSpinner(dialog, "createDateSpinner"));
        setFieldValue(dialog, "rangeDateMaxSpinner", invokeSpinner(dialog, "createDateSpinner"));
        setFieldValue(dialog, "topNDateSpinner", invokeSpinner(dialog, "createDateSpinner"));
        setFieldValue(dialog, "bottomNDateSpinner", invokeSpinner(dialog, "createDateSpinner"));

        assertEquals("NUMERIC", invokeString(dialog, "getSearchCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.INTEGER }));
        assertEquals("NUMERIC", invokeString(dialog, "getSearchCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.DOUBLE }));
        assertEquals("DATE", invokeString(dialog, "getSearchCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.DATE }));
        assertEquals("BOOLEAN", invokeString(dialog, "getSearchCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.BOOLEAN }));
        assertEquals("STRING", invokeString(dialog, "getSearchCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.EMPTY }));

        assertEquals("STRING", invokeString(dialog, "getRangeCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.STRING }));
        assertEquals("NUMERIC", invokeString(dialog, "getRangeCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.INTEGER }));
        assertEquals("DATE", invokeString(dialog, "getRangeCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.DATE }));
        assertEquals("UNSUPPORTED", invokeString(dialog, "getRangeCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.BOOLEAN }));

        assertEquals("STRING", invokeString(dialog, "getNCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.STRING }));
        assertEquals("NUMERIC", invokeString(dialog, "getNCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.DOUBLE }));
        assertEquals("DATE", invokeString(dialog, "getNCard", new Class<?>[] { DataType.class },
                new Object[] { DataType.DATE }));

        assertEquals(1.5,
                invokeDouble(dialog, "clampDouble", new Class<?>[] { double.class, Double.class, Double.class },
                        new Object[] { Double.NaN, 1.5, 3.5 }),
                0.0001);
        assertEquals(1.5,
                invokeDouble(dialog, "clampDouble", new Class<?>[] { double.class, Double.class, Double.class },
                        new Object[] { 1.0, 1.5, 3.5 }),
                0.0001);
        assertEquals(3.5,
                invokeDouble(dialog, "clampDouble", new Class<?>[] { double.class, Double.class, Double.class },
                        new Object[] { 9.0, 1.5, 3.5 }),
                0.0001);

        Date min = Date.from(LocalDateTime.of(2024, 1, 1, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant());
        Date max = Date.from(LocalDateTime.of(2024, 1, 2, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant());
        Date before = Date
                .from(LocalDateTime.of(2023, 12, 31, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant());
        Date after = Date
                .from(LocalDateTime.of(2024, 1, 3, 10, 0).atZone(java.time.ZoneId.systemDefault()).toInstant());
        assertSame(min, invoke(dialog, "clampDate", new Class<?>[] { Date.class, Date.class, Date.class },
                new Object[] { before, min, max }));
        assertSame(max, invoke(dialog, "clampDate", new Class<?>[] { Date.class, Date.class, Date.class },
                new Object[] { after, min, max }));

        JSpinner integerSpinner = invokeSpinner(dialog, "createIntegerSpinner");
        JSpinner doubleSpinner = invokeSpinner(dialog, "createDoubleSpinner");
        JSpinner dateSpinner = invokeSpinner(dialog, "createDateSpinner");
        setFieldValue(dialog, "searchDateSpinner", dateSpinner);
        assertNotNull(integerSpinner.getEditor());
        assertNotNull(doubleSpinner.getEditor());
        assertNotNull(dateSpinner.getEditor());

        Locale previous = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            ((javax.swing.JSpinner.NumberEditor) doubleSpinner.getEditor()).getTextField().setText("4.25");
            assertEquals(4.0, invokeDouble(dialog, "readDoubleSpinner", new Class<?>[] { JSpinner.class },
                    new Object[] { doubleSpinner }), 0.0001);

            integerSpinner.setValue(7);
            assertEquals(7, invokeInt(dialog, "readIntegerSpinner", new Class<?>[] { JSpinner.class },
                    new Object[] { integerSpinner }).intValue());
        } finally {
            Locale.setDefault(previous);
        }

        ((javax.swing.JSpinner.DateEditor) dateSpinner.getEditor()).getTextField().setText("2024-01-02 03:04:05");
        Timestamp ts = (Timestamp) invoke(dialog, "readTimestampSpinner", new Class<?>[] { JSpinner.class },
                new Object[] { dateSpinner });
        assertEquals(Timestamp.valueOf("2024-01-02 03:04:05"), ts);

        setFieldValue(dialog, "searchNumericSpinner", integerSpinner);
        setFieldValue(dialog, "rangeNumericMinSpinner", invokeSpinner(dialog, "createIntegerSpinner"));
        setFieldValue(dialog, "rangeNumericMaxSpinner", invokeSpinner(dialog, "createIntegerSpinner"));
        setFieldValue(dialog, "topNNumericSpinner", invokeSpinner(dialog, "createIntegerSpinner"));
        setFieldValue(dialog, "bottomNNumericSpinner", invokeSpinner(dialog, "createIntegerSpinner"));

        invoke(dialog, "updateNumericSpinnerBounds",
                new Class<?>[] { Double.class, Double.class, DataType.class, boolean.class },
                new Object[] { 1.2, 9.8, DataType.INTEGER, true });
        assertEquals(2.0, ((Number) integerSpinner.getValue()).doubleValue(), 0.0001);

        invoke(dialog, "updateDateSpinnerBounds",
                new Class<?>[] { Date.class, Date.class, boolean.class },
                new Object[] { min, max, true });
        assertTrue(min.equals(dateSpinner.getValue()));
    }

    @Test
    public void testAddAndRemoveFilters() throws Exception {
        FilterDialog dialog = newDialogShell(fakeTable(DataType.BOOLEAN));

        setFieldValue(dialog, "columnComboBox", new JComboBox<>(new String[] { "flag" }));
        getFieldTyped(dialog, "columnComboBox", JComboBox.class).setSelectedIndex(0);

        setFieldValue(dialog, "filterTypeComboBox", new JComboBox<>(
                new String[] { "SEARCH", "RANGE", "TOP_N", "BOTTOM_N", "SHOW_EMPTY", "HIDE_EMPTY", "SORT" }));
        getFieldTyped(dialog, "filterTypeComboBox", JComboBox.class).setSelectedIndex(FilterType.SEARCH.ordinal());

        setFieldValue(dialog, "logicOperatorComboBox", new JComboBox<>(new String[] { "AND", "OR" }));
        getFieldTyped(dialog, "logicOperatorComboBox", JComboBox.class).setSelectedIndex(1);

        setFieldValue(dialog, "searchBooleanComboBox", new JComboBox<>(new String[] { "true", "false" }));
        setFieldValue(dialog, "conditionsPanel", new JPanel());
        setFieldValue(dialog, "conditionRows", new ArrayList<FilterRow>());
        setFieldValue(dialog, "result", new ArrayList<Filter>());

        invokePanel(dialog, "buildConditionsListPanel");
        invoke(dialog, "addCurrentFilter");

        @SuppressWarnings("unchecked")
        List<Filter> result = (List<Filter>) getFieldTyped(dialog, "result", List.class);
        @SuppressWarnings("unchecked")
        List<FilterRow> rows = (List<FilterRow>) getFieldTyped(dialog, "conditionRows", List.class);
        JPanel conditionsPanel = getFieldTyped(dialog, "conditionsPanel", JPanel.class);
        assertEquals(1, result.size());
        assertEquals(1, rows.size());
        assertEquals(1, conditionsPanel.getComponentCount());
        assertEquals(1, result.get(0).getConditions().size());

        invoke(dialog, "removeConditionRow", new Class<?>[] { FilterRow.class }, new Object[] { rows.get(0) });
        assertEquals(0, result.size());
        assertEquals(0, rows.size());
        assertEquals(0, conditionsPanel.getComponentCount());
    }

    @Test
    public void testPublicSignatureStillExists() {
        assertNotNull(FilterDialog.class);
    }

    private static FilterDialog newDialogShell(DataTable table) throws Exception {
        FilterDialog dialog = allocate(FilterDialog.class);
        setFieldValue(dialog, "table", table);
        setFieldValue(dialog, "columnNames", new ArrayList<>(table.getColumnNames()));
        setFieldValue(dialog, "result", new ArrayList<Filter>());
        setFieldValue(dialog, "conditionsPanel", new JPanel());
        setFieldValue(dialog, "conditionRows", new ArrayList<FilterRow>());
        setIntField(dialog, "lastSelectedColumnIndex", -1);
        return dialog;
    }

    private static DataTable fakeTable(DataType type) throws Exception {
        DataTable table = allocate(DataTable.class);
        setFieldValue(table, "columnNames", new ArrayList<>(List.of("flag")));
        setFieldValue(table, "columnTypes", new ArrayList<>(List.of(type)));
        setFieldValue(table, "alias", "fake");
        setFieldValue(table, "tablePath", Path.of("fake.parquet").toFile());
        setLongField(table, "columnCount", 1L);
        setLongField(table, "rowCount", 0L);
        return table;
    }

    private static <T> T invoke(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (T) method.invoke(target);
    }

    private static <T> T invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (T) method.invoke(target, args);
    }

    private static <T> T getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(target);
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        unsafe().putObject(target, unsafe().objectFieldOffset(field), value);
    }

    private static JPanel invokePanel(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (JPanel) method.invoke(target, args);
    }

    private static JPanel invokePanel(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (JPanel) method.invoke(target);
    }

    private static String invokeString(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (String) method.invoke(target, args);
    }

    private static Double invokeDouble(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (Double) method.invoke(target, args);
    }

    private static Integer invokeInt(Object target, String methodName, Class<?>[] parameterTypes, Object[] args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (Integer) method.invoke(target, args);
    }

    private static JSpinner invokeSpinner(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (JSpinner) method.invoke(target);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getFieldTyped(Object target, String fieldName, Class<T> clazz) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(target);
    }

    private static void setFieldValue(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // fallback to Unsafe when direct set fails (for final fields)
            unsafe().putObject(target, unsafe().objectFieldOffset(field), value);
        }
    }

    private static void setLongField(Object target, String fieldName, long value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        unsafe().putLong(target, unsafe().objectFieldOffset(field), value);
    }

    private static void setIntField(Object target, String fieldName, int value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        unsafe().putInt(target, unsafe().objectFieldOffset(field), value);
    }

    private static <T> T allocate(Class<T> type) throws Exception {
        return (T) unsafe().allocateInstance(type);
    }

    private static sun.misc.Unsafe unsafe() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (sun.misc.Unsafe) field.get(null);
    }
}
