package fr.univrennes.istic.l2gen.application.core.services.export;

import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests pour AssetEntry
 */
public class AssetEntryTest {

    @Test
    public void testGettersAndSetters() {
        NoteBookText value = new NoteBookText("Texte de test");
        AssetEntry entry = new AssetEntry(value);

        assertEquals(value, entry.getValue());
        assertNull(entry.getImagePath());
        assertNull(entry.getChartPath());
        assertNull(entry.getChartImagePath());

        entry.setImagePath("images/picture.png");
        entry.setChartPath("charts/chart.svg");
        entry.setChartImagePath("charts/chart.png");

        assertEquals("images/picture.png", entry.getImagePath());
        assertEquals("charts/chart.svg", entry.getChartPath());
        assertEquals("charts/chart.png", entry.getChartImagePath());
    }
}
