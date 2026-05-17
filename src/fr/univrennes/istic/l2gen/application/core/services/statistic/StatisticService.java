package fr.univrennes.istic.l2gen.application.core.services.statistic;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;

import java.util.Optional;

public final class StatisticService {

        public static Optional<Double> getDistinctCount(DataTable table, int columnIndex) {
                return ExecuteStatistic.queryDouble(String.format(
                                "SELECT COUNT(DISTINCT %s) FROM %s WHERE %s IS NOT NULL",
                                table.getSQLColumnName(columnIndex),
                                table.getSQLName(),
                                table.getSQLColumnName(columnIndex)));
        }

        public static Optional<Double> getNonNullCount(DataTable table, int columnIndex) {
                return ExecuteStatistic.queryDouble(String.format(
                                "SELECT COUNT(%s) FROM %s WHERE %s IS NOT NULL",
                                table.getSQLColumnName(columnIndex),
                                table.getSQLName(),
                                table.getSQLColumnName(columnIndex)));
        }

        public static Optional<Double> getNullRate(DataTable table, int columnIndex) {
                return ExecuteStatistic.queryDouble(String.format(
                                "SELECT COUNT(*) FILTER (WHERE %s IS NULL) * 1.0 / NULLIF(COUNT(*), 0) FROM %s",
                                table.getSQLColumnName(columnIndex), table.getSQLName()));
        }

        public static Optional<Integer> getNullCount(DataTable table, int columnIndex) {
                return ExecuteStatistic.queryInteger(String.format(
                                "SELECT COUNT(*) FILTER (WHERE %s IS NULL) FROM %s",
                                table.getSQLColumnName(columnIndex), table.getSQLName()));
        }

        public static Optional<Double> getCardinalityRatio(DataTable table, int columnIndex) {
                return ExecuteStatistic.queryDouble(String.format(
                                "SELECT COUNT(DISTINCT %s) * 1.0 / NULLIF(COUNT(*), 0) FROM %s",
                                table.getSQLColumnName(columnIndex), table.getSQLName()));
        }

        public static String getSummary(DataTable table, int columnIndex) {
                StringBuilder summary = new StringBuilder();
                summary.append(Lang.get("statistics.summary.column_name", table.getColumnName(columnIndex)))
                                .append("\n");
                summary.append(Lang.get("statistics.summary.type", table.getColumnType(columnIndex))).append("\n");

                summary.append(Lang.get("statistics.summary.native_type", table.getColumnType(columnIndex).toSQL()))
                                .append("\n");
                summary.append(Lang.get("statistics.summary.null_rate",
                                formatPercentage(getNullRate(table, columnIndex).orElse(Double.NaN)))).append("\n");
                summary.append(Lang.get("statistics.summary.distinct_count",
                                getDistinctCount(table, columnIndex).orElse(Double.NaN))).append("\n");
                summary.append(Lang.get("statistics.summary.cardinality_ratio",
                                formatPercentage(getCardinalityRatio(table, columnIndex).orElse(Double.NaN))))
                                .append("\n");

                return summary.toString();
        }

        private static String formatPercentage(double value) {
                if (Double.isNaN(value)) {
                        return "N/A";
                }
                return String.format("%.2f%%", value * 100);
        }

}