package fr.univrennes.istic.l2gen.application.gui.dialog.input;

import org.junit.Test;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class InputSelectDialogTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildReadAndInvalidSelection() throws Exception {
        List<String> options = Arrays.asList("alpha", "beta", "gamma");
        InputSelectDialog dialog = new InputSelectDialog(options);

        JComponent component = dialog.build("Choisir");
        assertNotNull(component);
        assertEquals(2, ((JPanel) component).getComponentCount());

        JLabel label = (JLabel) ((JPanel) component).getComponent(0);
        assertEquals("Choisir", label.getText());

        JComboBox<String> comboBox = getField(dialog, "comboBox", JComboBox.class);
        assertEquals(3, comboBox.getItemCount());
        assertEquals("beta", comboBox.getItemAt(1));

        comboBox.setSelectedItem("gamma");
        assertEquals("gamma", dialog.readValue());

        comboBox.setSelectedItem(null);
        try {
            dialog.readValue();
            fail("Une selection vide doit etre refusee");
        } catch (IllegalArgumentException expected) {
        }

        dialog.onInvalidInput();
    }

    @Test
    public void testPublicShowSignatureExists() throws Exception {
        Method method = InputSelectDialog.class.getMethod("show", List.class, String.class, String.class,
                String.class);
        assertNotNull(method);
        assertSame(java.util.Optional.class, method.getReturnType());
    }

    @SuppressWarnings("unchecked")
    private static <T> T getField(Object target, String name, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }
}