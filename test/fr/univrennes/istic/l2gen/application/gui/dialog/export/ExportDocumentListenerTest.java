package fr.univrennes.istic.l2gen.application.gui.dialog.export;

import org.junit.Test;

import javax.swing.event.DocumentEvent;

import static org.junit.Assert.assertEquals;

/**
 * Tests pour ExportDocumentListener
 */
public class ExportDocumentListenerTest {

    @Test
    public void testDefaultMethodsForwardToUpdate() throws Exception {
        CounterListener listener = new CounterListener();

        listener.insertUpdate(null);
        listener.removeUpdate(null);
        listener.changedUpdate(null);

        assertEquals(3, listener.calls);
    }

    private static final class CounterListener implements ExportDocumentListener {
        private int calls = 0;

        @Override
        public void update(DocumentEvent event) {
            calls++;
        }
    }
}
