package fr.univrennes.istic.l2gen.application.core.filter;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilterTest {

    @Test
    public void sortTest() {
        Filter f = Filter.sort(0, true);
        assertEquals(f.getType(), FilterType.SORT);
        assertEquals(f.getColumnIndex(), 0);
        assertEquals(f.isAscending(), true);
    }

    @Test
    public void topNTest() {
        Filter f = Filter.topN(0, 15);
        assertEquals(f.getN(), 15);
        assertEquals(f.getColumnIndex(), 0);
        assertEquals(f.getType(), FilterType.TOP_N);
    }

    @Test
    public void bottomNTest() {
        Filter f = Filter.bottomN(0, 15);
        assertEquals(f.getN(), 15);
        assertEquals(f.getType(), FilterType.BOTTOM_N);
        assertEquals(f.getColumnIndex(), 0);
    }

    @Test
    public void byRangeTest() {
        Filter f = Filter.byRange(0, 10, 100);
        assertEquals(f.getType(), FilterType.RANGE);
        assertEquals(f.getMin(), 10, 0.001);
        assertEquals(f.getMax(), 100, 0.001);
        assertEquals(f.getColumnIndex(), 0);
    }

    @Test
    public void showEmptyTest() {
        Filter f = Filter.showEmpty(0);
        assertEquals(f.getType(), FilterType.EMPTY);
        assertEquals(f.getColumnIndex(), 0);
    }

    @Test
    public void hideEmptyTest() {
        Filter f = Filter.hideEmpty(0);
        assertEquals(f.getType(), FilterType.NOT_EMPTY);
        assertEquals(f.getColumnIndex(), 0);
    }

    @Test
    public void searchTest() {
        Filter f = Filter.search(0, "string");
        assertEquals(f.getType(), FilterType.SEARCH);
        assertEquals(f.getColumnIndex(), 0);
        assertEquals(f.getMax(), Double.NaN, 0.001);
        assertEquals(f.getMin(), Double.NaN, 0.001);
    }

}
