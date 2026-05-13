package fr.univrennes.istic.l2gen.application.core.services.statistic;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.duckdb.DuckDBConnection;

import fr.univrennes.istic.l2gen.application.Pangol1;
import fr.univrennes.istic.l2gen.application.core.TaskStatus;
import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.core.config.Log;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.core.table.DataType;

public class StringStatisticService {

    public static Optional<Integer> getSumLength(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryInteger(String.format(
                "SELECT SUM(LENGTH(%s)) FROM %s WHERE %s IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex)));
    }

    public static Optional<Integer> getMeanLength(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryInteger(String.format(
                "SELECT LENGTH(CAST(AVG(LENGTH(%s)) AS VARCHAR)) FROM %s WHERE %s IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex)));
    }

    public static Optional<Integer> getMinLength(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryInteger(String.format(
                "SELECT LENGTH(CAST(MIN(LENGTH(%s)) AS VARCHAR)) FROM %s WHERE %s IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex)));
    }

    public static Optional<Integer> getMaxLength(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryInteger(String.format(
                "SELECT LENGTH(CAST(MAX(LENGTH(%s)) AS VARCHAR)) FROM %s WHERE %s IS NOT NULL",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex)));
    }

    public static Optional<Double> getEntropyEstimate(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryDouble(String.format(
                "SELECT -SUM((COUNT(*) * 1.0 / (SELECT COUNT(*) FROM %s WHERE %s IS NOT NULL)) * "
                        + "LOG(COUNT(*) * 1.0 / (SELECT COUNT(*) FROM %s WHERE %s IS NOT NULL))) "
                        + "FROM %s WHERE %s IS NOT NULL GROUP BY %s",
                table.getSQLName(), table.getSQLColumnName(columnIndex),
                table.getSQLName(), table.getSQLColumnName(columnIndex),
                table.getSQLName(), table.getSQLColumnName(columnIndex), table.getSQLColumnName(columnIndex)));
    }

    public static boolean hasCategories(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isCategorical()) {
            return false;
        }

        String taskId = Pangol1.getController().addTask(
                Lang.get("task.stats.count_categories", table.getColumnName(columnIndex)),
                TaskStatus.PENDING);
        String query = String.format(
                "SELECT COUNT(DISTINCT %s) FROM %s WHERE %s IS NOT NULL",
                table.getSQLColumnName(columnIndex),
                table.getSQLName(),
                table.getSQLColumnName(columnIndex));

        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {

            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.RUNNING);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                int distinctCount = resultSet.getInt(1);
                boolean hasCategories = distinctCount > 0 && distinctCount < table.getRowCount();
                Pangol1.getController().updateTaskStatus(taskId, TaskStatus.SUCCESS);
                return hasCategories;
            }

        } catch (Exception e) {
            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.FAILED);
            Log.debug("Failed to execute query: " + query, e);
        }

        Pangol1.getController().updateTaskStatus(taskId, TaskStatus.SUCCESS);
        return false;
    }

    public static List<String> getCategories(DataTable table, int columnIndex) {
        if (!table.getColumnType(columnIndex).isCategorical()) {
            return List.of();
        }

        String taskId = Pangol1.getController().addTask(
                Lang.get("task.stats.fetch_categories", table.getColumnName(columnIndex)),
                TaskStatus.PENDING);
        String query = String.format(
                "SELECT DISTINCT %s FROM %s WHERE %s IS NOT NULL ORDER BY %s",
                table.getSQLColumnName(columnIndex),
                table.getSQLName(),
                table.getSQLColumnName(columnIndex),
                table.getSQLColumnName(columnIndex));

        List<String> categories = new ArrayList<>();
        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {
            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.RUNNING);
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String value = resultSet.getString(1);
                if (!resultSet.wasNull()) {
                    categories.add(value);
                }
            }

            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.SUCCESS);
        } catch (Exception e) {
            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.FAILED);
            Log.debug("Failed to execute query: " + query, e);
        }

        return categories;
    }

    public static Optional<String> getMostFrequentValue(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Optional.empty();
        }
        return ExecuteStatistic.queryString(String.format(
                "SELECT %s FROM %s WHERE %s IS NOT NULL GROUP BY %s ORDER BY COUNT(*) DESC LIMIT 1",
                table.getSQLColumnName(columnIndex), table.getSQLName(), table.getSQLColumnName(columnIndex),
                table.getSQLColumnName(columnIndex)));
    }

    public static Map<String, Long> getFrequencyDistribution(DataTable table, int columnIndex) {
        if (table.getColumnType(columnIndex) != DataType.STRING) {
            return Collections.emptyMap();
        }

        String columnSqlName = table.getSQLColumnName(columnIndex);
        String query = String.format(
                "SELECT %s, COUNT(*) AS freq FROM %s WHERE %s IS NOT NULL GROUP BY %s ORDER BY freq DESC",
                columnSqlName, table.getSQLName(), columnSqlName, columnSqlName);

        Map<String, Long> frequencyMap = new HashMap<>();
        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String value = resultSet.getString(1);
                long frequency = resultSet.getLong(2);
                if (!resultSet.wasNull()) {
                    frequencyMap.put(value, frequency);
                }
            }
        } catch (Exception e) {
            Log.debug("Failed to execute query: " + query, e);
        }

        return frequencyMap;
    }
}
