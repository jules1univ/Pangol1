package fr.univrennes.istic.l2gen.application.core.services;

import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.core.table.DataType;

public class StatisticServiceTest {

    private final List<File> tempPaths = new ArrayList<>();
    private final List<DataTable> openedTables = new ArrayList<>();

    @After
    public void cleanup() {
        for (DataTable table : openedTables) {
            try {
                table.close();
            } catch (Exception e) {
            }
        }
        for (File path : tempPaths) {
            try {
                Files.deleteIfExists(path.toPath());
            } catch (Exception e) {
            }
        }
    }

    @Test
    public void testComputeBaseAndSummary() throws Exception {
        DataTable table = createTable();
        openedTables.add(table);

        Assert.assertEquals("1,00", StatisticService.computeBase(table, 0, StatisticOp.MIN).orElseThrow());
        Assert.assertEquals("8,00", StatisticService.computeBase(table, 1, StatisticOp.MAX).orElseThrow());
        Assert.assertEquals("2,50", StatisticService.computeBase(table, 0, StatisticOp.AVG).orElseThrow());
        Assert.assertEquals("4,00", StatisticService.computeBase(table, 0, StatisticOp.COUNT).orElseThrow());
        Assert.assertTrue(StatisticService.computeBase(table, 3, StatisticOp.MIN).orElseThrow().length() > 0);
        Assert.assertTrue(StatisticService.computeBase(table, 3, StatisticOp.MAX).orElseThrow().length() > 0);
        Assert.assertEquals("3,00 length", StatisticService.computeBase(table, 2, StatisticOp.AVG).orElseThrow());
        Assert.assertFalse(StatisticService.computeBase(table, 3, StatisticOp.AVG).isPresent());

        String summary = StatisticService.computeSummary(table, 2);
        Assert.assertTrue(summary.contains("Min:"));
        Assert.assertTrue(summary.contains("Average:"));
        Assert.assertTrue(summary.contains("Type: STRING"));
        Assert.assertTrue(summary.contains("Native Type: VARCHAR"));
    }

    @Test
    public void testDistributionMetrics() throws Exception {
        DataTable table = createTable();
        openedTables.add(table);

        Assert.assertEquals(1.0, StatisticService.computeCorrelation(table, 0, 1).orElseThrow(), 0.0001);
        Assert.assertTrue(StatisticService.computeCorrelation(table, 0, 2).isEmpty());

        Assert.assertTrue(StatisticService.computeCoefficientOfVariation(table, 0).isPresent());
        Assert.assertTrue(StatisticService.computeSkewness(table, 0).isPresent());
        Assert.assertTrue(StatisticService.computeInterquartileRange(table, 0).isPresent());

        Assert.assertEquals(0.25, StatisticService.computeNullRate(table, 2).orElseThrow(), 0.0001);
        Assert.assertEquals(1.0, StatisticService.computeCardinalityRatio(table, 0).orElseThrow(), 0.0001);
    }

    @Test
    public void testNonNumericMetricsReturnEmpty() throws Exception {
        DataTable table = createTable();
        openedTables.add(table);

        Assert.assertTrue(StatisticService.computeCoefficientOfVariation(table, 2).isEmpty());
        Assert.assertTrue(StatisticService.computeSkewness(table, 2).isEmpty());
        Assert.assertTrue(StatisticService.computeInterquartileRange(table, 2).isEmpty());
    }

    private DataTable createTable() throws Exception {
        File directory = Files.createTempDirectory("stats-table").toFile();
        tempPaths.add(directory);
        File parquet = new File(directory, "stats.parquet");
        tempPaths.add(parquet);

        String select = "SELECT * FROM (VALUES "
                + "(1, CAST(2.0 AS DOUBLE), 'aaa', DATE '2024-01-01', true),"
                + "(2, CAST(4.0 AS DOUBLE), 'bbb', DATE '2024-01-02', false),"
                + "(3, CAST(6.0 AS DOUBLE), NULL, DATE '2024-01-03', true),"
                + "(4, CAST(8.0 AS DOUBLE), 'ccc', DATE '2024-01-04', false)"
                + ") AS t(id, val, txt, d, flag)";

        String path = parquet.getAbsolutePath().replace("\\", "/").replace("'", "''");
        try (Connection connection = DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {
            statement.execute("COPY (" + select + ") TO '" + path + "' (FORMAT PARQUET)");
        }

        DataTable table = DataTable.of(parquet);
        Assert.assertNotNull(table);
        Assert.assertEquals(DataType.INTEGER, table.getColumnType(0));
        Assert.assertEquals(DataType.DOUBLE, table.getColumnType(1));
        Assert.assertEquals(DataType.STRING, table.getColumnType(2));
        Assert.assertEquals(DataType.DATE, table.getColumnType(3));
        Assert.assertEquals(DataType.BOOLEAN, table.getColumnType(4));
        return table;
    }
}