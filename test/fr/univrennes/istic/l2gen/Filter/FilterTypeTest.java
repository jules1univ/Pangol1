package fr.univrennes.istic.l2gen.Filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.filter.FilterType;

public class FilterTypeTest {
    @Test
    public void testEnumValuesCount() {
        assertEquals("L'enum FilterType devrait avoir 7 valeurs", 7, FilterType.values().length);
    }

    @Test
    public void testEnumValueOf() {
        assertEquals(FilterType.SORT, FilterType.valueOf("SORT"));
        assertEquals(FilterType.TOP_N, FilterType.valueOf("TOP_N"));
        assertEquals(FilterType.BOTTOM_N, FilterType.valueOf("BOTTOM_N"));
        assertEquals(FilterType.RANGE, FilterType.valueOf("RANGE"));
        assertEquals(FilterType.EMPTY, FilterType.valueOf("EMPTY"));
        assertEquals(FilterType.NOT_EMPTY, FilterType.valueOf("NOT_EMPTY"));
        assertEquals(FilterType.SEARCH, FilterType.valueOf("SEARCH"));
    }
}
