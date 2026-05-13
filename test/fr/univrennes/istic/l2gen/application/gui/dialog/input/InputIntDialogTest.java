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
 * Tests pour InputIntDialog.
 */
public class InputIntDialogTest {

    @Test
    public void testBuildRangeReadAndSelection() throws Exception {
        InputIntDialog dialog = new InputIntDialog();
        dialog.setRange(2, 5);

        JComponent component = dialog.build("Valeur");
        assertNotNull(component);
        assertEquals(2, ((JPanel) component).getComponentCount());

        JSpinner spinner = getField(dialog, "spinner", JSpinner.class);
        SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        assertEquals(2, ((Number) model.getMinimum()).intValue());
        assertEquals(5, ((Number) model.getMaximum()).intValue());
        assertEquals(2, ((Number) model.getValue()).intValue());

        spinner.setValue(4);
        assertEquals(Integer.valueOf(4), dialog.readValue());

        JTextField textField = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
        textField.setText("123");
        dialog.onInvalidInput();
        assertEquals(0, textField.getSelectionStart());
        assertEquals(3, textField.getSelectionEnd());
    }

    @Test
    public void testPublicShowSignaturesExist() throws Exception {
        assertSame(java.util.Optional.class,
                InputIntDialog.class.getMethod("show", String.class, String.class, String.class).getReturnType());
        assertSame(java.util.Optional.class,
                InputIntDialog.class.getMethod("show", String.class, String.class, String.class, int.class,
                        int.class).getReturnType());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String name, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}