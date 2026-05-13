package fr.univrennes.istic.l2gen.application.gui.dialog.task;

import org.junit.Test;

import java.awt.Dimension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Tests pour ScrollingLabel
 */
public class ScrollingLabelTest {

    @Test
    public void testConstructorInitializesSizes() {
        ScrollingLabel label = new ScrollingLabel("Long texte", 120);

        assertNotNull(label.getPreferredSize());
        assertEquals(new Dimension(120, 18), label.getPreferredSize());
        assertEquals(new Dimension(120, 18), label.getMaximumSize());
        assertFalse(label.isOpaque());
    }
}
