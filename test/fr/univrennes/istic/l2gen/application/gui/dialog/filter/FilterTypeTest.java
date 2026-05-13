package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests pour FilterType
 */
public class FilterTypeTest {

    @Test
    public void testEnumValuesOrder() {
        assertArrayEquals(
                new FilterType[] {
                        FilterType.SEARCH,
                        FilterType.RANGE,
                        FilterType.TOP_N,
                        FilterType.BOTTOM_N,
                        FilterType.SHOW_EMPTY,
                        FilterType.HIDE_EMPTY,
                        FilterType.SORT },
                FilterType.values());
    }
}
