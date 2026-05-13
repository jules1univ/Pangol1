package fr.univrennes.istic.l2gen.application.gui.dialog.input;

import org.junit.Test;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Tests pour InputDateDialog.
 */
public class InputDateDialogTest {

    @Test
    public void testBuildRangeReadAndSelection() throws Exception {
        Date min = new Date(1_000L);
        Date max = new Date(5_000L);

        InputDateDialog dialog = new InputDateDialog();
        dialog.setRange(min, max);

        JComponent component = dialog.build("Date");
        assertNotNull(component);
        assertEquals(2, ((JPanel) component).getComponentCount());

        JSpinner spinner = getField(dialog, "spinner", JSpinner.class);
        SpinnerDateModel model = (SpinnerDateModel) spinner.getModel();
        assertEquals(min, model.getStart());
        assertEquals(max, model.getEnd());
        assertEquals(min, model.getDate());

        Date expected = new Date(3_000L);
        spinner.setValue(expected);
        assertEquals(expected, dialog.readValue());

        JTextField textField = ((JSpinner.DateEditor) spinner.getEditor()).getTextField();
        textField.setText("2026-05-12 10:15:30");
        dialog.onInvalidInput();
        assertEquals(0, textField.getSelectionStart());
        assertEquals(textField.getText().length(), textField.getSelectionEnd());
    }

    @Test
    public void testPublicShowSignaturesExist() throws Exception {
        assertSame(java.util.Optional.class,
                InputDateDialog.class.getMethod("show", String.class, String.class, String.class).getReturnType());
        assertSame(java.util.Optional.class,
                InputDateDialog.class.getMethod("show", String.class, String.class, String.class, Date.class,
                        Date.class).getReturnType());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String name, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}