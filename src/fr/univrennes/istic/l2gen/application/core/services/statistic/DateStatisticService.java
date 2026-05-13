package fr.univrennes.istic.l2gen.application.core.services.statistic;

import java.sql.Timestamp;
import java.util.Optional;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.core.table.DataType;

public class DateStatisticService {

    public static Optional<Timestamp> getMin(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.DATE) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryTimestamp(String.format(
                "SELECT MIN(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Timestamp> getMax(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.DATE) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryTimestamp(String.format(
                "SELECT MAX(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Timestamp> getMedian(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.DATE) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryTimestamp(String.format(
                "SELECT MEDIAN(val) FROM (SELECT %s AS val FROM %s) WHERE val IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName()));
    }

    public static Optional<Timestamp> getMostFrequentValue(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.DATE) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryTimestamp(String.format(
                "SELECT %s FROM %s WHERE %s IS NOT NULL GROUP BY %s ORDER BY COUNT(*) DESC LIMIT 1",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex),
                table.getSQLColumnName(columnIndex)));
    }
}
