package fr.univrennes.istic.l2gen.application.gui.dialog;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests pour DialogBase
 */
public class DialogBaseTest {

    @Test
    public void testConstantsArePositive() {
        assertTrue(DialogBase.WIDTH > 0);
        assertTrue(DialogBase.HEIGHT > 0);
    }
}
