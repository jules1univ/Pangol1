package fr.univrennes.istic.l2gen.application.core.filter;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;

public final class FilterBuilder {

    public static String count(DataTable table) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");

        return sql.toString();
    }

    public static String query(DataTable table, long limit, long offset) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + table.getSQLName());

        return sql.toString();
    }
}