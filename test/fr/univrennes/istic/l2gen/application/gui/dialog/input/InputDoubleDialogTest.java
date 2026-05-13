package fr.univrennes.istic.l2gen.application.gui.dialog.input;

import org.junit.Test;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Tests pour InputDoubleDialog.
 */
public class InputDoubleDialogTest {

    @Test
    public void testBuildRangeReadAndSelection() throws Exception {
        InputDoubleDialog dialog = new InputDoubleDialog();
        dialog.setRange(1.5, 3.5);

        JComponent component = dialog.build("Valeur");
        assertNotNull(component);
        assertEquals(2, ((JPanel) component).getComponentCount());

        JSpinner spinner = getField(dialog, "spinner", JSpinner.class);
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        assertEquals(1.5, ((Number) model.getMinimum()).doubleValue(), 0.0);
        assertEquals(3.5, ((Number) model.getMaximum()).doubleValue(), 0.0);
        assertEquals(1.5, ((Number) model.getValue()).doubleValue(), 0.0);

        spinner.setValue(2.25);
        assertEquals(Double.valueOf(2.25), dialog.readValue());

        JTextField textField = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        textField.setText("2.5");
        dialog.onInvalidInput();
        assertEquals(0, textField.getSelectionStart());
        assertEquals(3, textField.getSelectionEnd());
    }

    @Test
    public void testPublicShowSignaturesExist() throws Exception {
        assertSame(java.util.Optional.class,
                InputDoubleDialog.class.getMethod("show", String.class, String.class, String.class).getReturnType());
        assertSame(java.util.Optional.class,
                InputDoubleDialog.class.getMethod("show", String.class, String.class, String.class, double.class,
                        double.class).getReturnType());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String name, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}