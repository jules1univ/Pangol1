package fr.univrennes.istic.l2gen.application.gui.dialog.input;

import org.junit.Test;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.lang.reflect.Method;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests de structure pour AbstractInputDialog.
 */
public class AbstractInputDialogTest {

    @Test
    public void testShowDialogSignatureExists() throws Exception {
        Method method = AbstractInputDialog.class.getMethod("showDialog", String.class, String.class,
                String.class);

        assertNotNull(method);
        assertTrue(Optional.class.isAssignableFrom(method.getReturnType()));
    }

    @Test
    public void testProtectedHooksAreDeclared() throws Exception {
        assertNotNull(AbstractInputDialog.class.getDeclaredMethod("build", String.class));
        assertNotNull(AbstractInputDialog.class.getDeclaredMethod("readValue"));
        assertNotNull(AbstractInputDialog.class.getDeclaredMethod("onInvalidInput"));
    }

    @Test
    public void testProbeSubclassCanOverrideHooks() throws Exception {
        ProbeDialog dialog = new ProbeDialog();

        assertTrue(dialog.build("message") instanceof JPanel);
        dialog.onInvalidInput();
        assertTrue(dialog.invalidInputHandled);
    }

    private static final class ProbeDialog extends AbstractInputDialog<String> {

        private boolean invalidInputHandled;

        @Override
        protected JComponent build(String message) {
            return new JPanel();
        }

        @Override
        protected String readValue() {
            return "ok";
        }

        @Override
        protected void onInvalidInput() {
            invalidInputHandled = true;
        }
    }
}