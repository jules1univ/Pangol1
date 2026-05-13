package fr.univrennes.istic.l2gen.application.core.services.export;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests pour ExportTheme
 */
public class ExportThemeTest {

    @Test
    public void testThemeMetadataIsStable() {
        assertEquals("theme-linen", ExportTheme.LINEN.getCssClass());
        assertEquals("linen", ExportTheme.LINEN.getId());
        assertEquals("theme-ink", ExportTheme.INK.getCssClass());
        assertEquals("ink", ExportTheme.INK.getId());
        assertNotNull(ExportTheme.LINEN.getLabel());
        assertTrue(ExportTheme.PROPERTY_KEY.contains("export.theme"));
    }

    @Test
    public void testFromIdAcceptsSeveralForms() {
        assertEquals(ExportTheme.LINEN, ExportTheme.fromId(null));
        assertEquals(ExportTheme.LINEN, ExportTheme.fromId(""));
        assertEquals(ExportTheme.LINEN, ExportTheme.fromId("   "));
        assertEquals(ExportTheme.INK, ExportTheme.fromId("ink"));
        assertEquals(ExportTheme.INK, ExportTheme.fromId("theme-ink"));
        assertEquals(ExportTheme.SAGE, ExportTheme.fromId("SAGE"));
        assertEquals(ExportTheme.HARBOR, ExportTheme.fromId("theme-harbor"));
        assertEquals(ExportTheme.SANDSTONE, ExportTheme.fromId("sandstone"));
        assertEquals(ExportTheme.LINEN, ExportTheme.fromId("unknown-value"));
    }
}
