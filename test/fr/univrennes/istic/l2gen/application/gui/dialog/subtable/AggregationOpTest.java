package fr.univrennes.istic.l2gen.application.gui.dialog.subtable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests pour AggregationOp
 */
public class AggregationOpTest {

    @Test
    public void testSuffixAndSqlFormatting() {
        assertEquals("none", AggregationOp.NONE.getSuffix());
        assertEquals("col", AggregationOp.NONE.toSql("col"));

        assertEquals("SUM(col)", AggregationOp.SUM.toSql("col"));
        assertEquals("AVG(col)", AggregationOp.AVG.toSql("col"));
        assertEquals("MIN(col)", AggregationOp.MIN.toSql("col"));
        assertEquals("MAX(col)", AggregationOp.MAX.toSql("col"));
        assertEquals("COUNT(col)", AggregationOp.COUNT.toSql("col"));
        assertEquals("COUNT(DISTINCT col)", AggregationOp.COUNT_DISTINCT.toSql("col"));
    }

    @Test
    public void testLabelsAreNotBlank() {
        for (AggregationOp op : AggregationOp.values()) {
            assertFalse(op.getLabel().isBlank());
        }
    }
}
