package fr.univrennes.istic.l2gen.application.core.services.statistic;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.function.Function;

import org.duckdb.DuckDBConnection;

import fr.univrennes.istic.l2gen.application.Pangol1;
import fr.univrennes.istic.l2gen.application.core.TaskStatus;
import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.core.config.Log;

public final class ExecuteStatistic {

    public static Optional<Object> queryAny(String query, Function<ResultSet, Object> executor) {
        String taskId = Pangol1.getController().addTask(Lang.get("task.execute_statistics"), TaskStatus.PENDING);
        try (DuckDBConnection connection = (DuckDBConnection) DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {

            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.RUNNING);
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Object value = executor.apply(resultSet);
                Pangol1.getController().updateTaskStatus(taskId, TaskStatus.SUCCESS);
                return resultSet.wasNull() ? Optional.empty() : Optional.of(value);
            }

        } catch (Exception e) {
            Pangol1.getController().updateTaskStatus(taskId, TaskStatus.FAILED);
            Log.debug("Failed to execute query: " + query, e);
        }
        return Optional.empty();
    }

    public static Optional<Timestamp> queryTimestamp(String query) {
        return queryAny(query, resultSet -> {
            try {
                Timestamp value = resultSet.getTimestamp(1);
                return resultSet.wasNull() ? null : value;
            } catch (Exception e) {
                Log.debug("Failed to extract timestamp from result set", e);
                return null;
            }
        }).map(value -> (Timestamp) value);
    }

    public static Optional<Integer> queryInteger(String query) {
        return queryAny(query, resultSet -> {
            try {
                int value = resultSet.getInt(1);
                return resultSet.wasNull() ? null : value;
            } catch (Exception e) {
                Log.debug("Failed to extract integer from result set", e);
                return null;
            }
        }).map(value -> (Integer) value);
    }

    public static Optional<Double> queryDouble(String query) {
        return queryAny(query, resultSet -> {
            try {
                double value = resultSet.getDouble(1);
                return resultSet.wasNull() ? null : value;
            } catch (Exception e) {
                Log.debug("Failed to extract double from result set", e);
                return null;
            }
        }).map(value -> (Double) value);
    }

    public static Optional<String> queryString(String query) {
        return queryAny(query, resultSet -> {
            try {
                String value = resultSet.getString(1);
                return resultSet.wasNull() ? null : value;
            } catch (Exception e) {
                Log.debug("Failed to extract string from result set", e);
                return null;
            }
        }).map(value -> (String) value);
    }
}
