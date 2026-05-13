package fr.univrennes.istic.l2gen.application.gui.dialog.input;

import org.junit.Test;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * Tests pour InputStringDialog.
 */
public class InputStringDialogTest {

    @Test
    public void testBuildReadAndInvalidSelection() throws Exception {
        InputStringDialog dialog = new InputStringDialog();

        JComponent component = dialog.build("Nom");
        assertTruePanel(component);

        JLabel label = (JLabel) ((JPanel) component).getComponent(0);
        assertEquals("Nom", label.getText());

        JTextField inputField = getField(dialog, "inputField", JTextField.class);
        assertEquals(28, inputField.getColumns());

        inputField.setText("  Alice  ");
        assertEquals("Alice", dialog.readValue());

        inputField.setText("   ");
        try {
            dialog.readValue();
            fail("Une entree vide doit etre refusee");
        } catch (IllegalArgumentException expected) {
            // attendu
        }

        inputField.setText("Bonjour");
        dialog.onInvalidInput();
        assertEquals(0, inputField.getSelectionStart());
        assertEquals(inputField.getText().length(), inputField.getSelectionEnd());
    }

    @Test
    public void testPublicShowSignatureExists() throws Exception {
        Method method = InputStringDialog.class.getMethod("show", String.class, String.class, String.class);
        assertNotNull(method);
        assertSame(java.util.Optional.class, method.getReturnType());
    }

    private static void assertTruePanel(JComponent component) {
        assertNotNull(component);
        assertEquals(2, ((JPanel) component).getComponentCount());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String name, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}