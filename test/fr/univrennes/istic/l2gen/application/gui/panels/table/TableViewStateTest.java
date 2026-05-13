package fr.univrennes.istic.l2gen.application.gui.panels.table;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests pour TableViewState
 */
public class TableViewStateTest {

    @Test
    public void testEnumValuesOrder() {
        assertArrayEquals(
                new TableViewState[] {
                        TableViewState.EMPTY,
                        TableViewState.LIST,
                        TableViewState.TABLE },
                TableViewState.values());
    }
}
