package fr.univrennes.istic.l2gen.application.core.services.statistic;

import java.util.Locale;
import java.util.Optional;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;

public final class NumericStatisticService {

    public static Optional<Double> getCorrelation(DataTable table, int columnIndexX, int columnIndexY) {
        if (!table.getColumnType(columnIndexX).isNumeric()
                || !table.getColumnType(columnIndexY).isNumeric()) {
            return Optional.empty();
        }

        return ExecuteStatistic.queryDouble(String.format(
                "SELECT CORR(x, y) FROM (" +
                        "SELECT %s AS x, %s AS y FROM %s) " +
                        "WHERE x IS NOT NULL AND y IS NOT NULL",
                table.getSQLColumnName(columnIndexX), table.getSQLColumnName(columnIndexY), table.getSQLName()));
    }

    public static Optional<Double> getCoefficientOfVariation(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT CASE WHEN mean_val = 0 THEN NULL " +
                        "ELSE stddev_val / mean_val END " +
                        "FROM (" +
                        "SELECT AVG(val) AS mean_val, STDDEV_SAMP(val) AS stddev_val " +
                        "FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL)",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getSkewness(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT SKEWNESS(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getKurtosis(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT KURTOSIS(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getInterquartileRange(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT QUANTILE_CONT(val, 0.75) - QUANTILE_CONT(val, 0.25) " +
                        "FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getIntercentileRangeRatio(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT CASE WHEN p50 = 0 THEN NULL " +
                        "ELSE (p90 - p10) / ABS(p50) END " +
                        "FROM (" +
                        "SELECT " +
                        "QUANTILE_CONT(val, 0.1) AS p10, " +
                        "QUANTILE_CONT(val, 0.5) AS p50, " +
                        "QUANTILE_CONT(val, 0.9) AS p90 " +
                        "FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL)",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getMedian(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT MEDIAN(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getStandardDeviation(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT STDDEV_SAMP(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getVariance(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT VAR_SAMP(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getPercentile(DataTable table, int columnIndex, double percentile) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        if (percentile < 0.0 || percentile > 1.0) {
            throw new IllegalArgumentException("Percentile must be between 0.0 and 1.0");
        }
        return ExecuteStatistic.queryDouble(String.format(
                Locale.US,
                "SELECT QUANTILE_CONT(val, %f) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                percentile,
                table.getSQLColumnName(columnIndex),
                table.getSQLName()));
    }

    public static Optional<Double> getMean(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT AVG(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getSum(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT SUM(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getMin(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT MIN(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getMax(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT MAX(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getGeometricMean(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT EXP(AVG(LN(val))) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL AND val > 0",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getHarmonicMean(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT CASE WHEN SUM(1.0 / val) = 0 THEN NULL " +
                        "ELSE COUNT(val) / SUM(1.0 / val) END " +
                        "FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL AND val != 0",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getRange(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT MAX(val) - MIN(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getMeanAbsoluteDeviation(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT AVG(ABS(val - avg_val)) FROM (" +
                        "SELECT val, AVG(val) OVER () AS avg_val " +
                        "FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL)",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getRootMeanSquare(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isNumeric()) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT SQRT(AVG(val * val)) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Double> getMostFrequentValueAsDouble(DataTable table, int columnIndex) {
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT %s FROM %s WHERE %s IS NOT NULL GROUP BY %s ORDER BY COUNT(*) DESC LIMIT 1",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex),
                table.getSQLColumnName(columnIndex)));
    }

}
