package fr.univrennes.istic.l2gen.application.gui.dialog.export;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests pour ExportFormat
 */
public class ExportFormatTest {

    @Test
    public void testExtensionsAndLabelsAreStable() {
        assertEquals("html", ExportFormat.HTML.getExtension());
        assertEquals("pdf", ExportFormat.PDF.getExtension());
        assertEquals("md", ExportFormat.MD.getExtension());

        assertFalse(ExportFormat.HTML.getLabel().isBlank());
        assertFalse(ExportFormat.PDF.getLabel().isBlank());
        assertFalse(ExportFormat.MD.getLabel().isBlank());
    }
}
