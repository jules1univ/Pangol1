package fr.univrennes.istic.l2gen.application.core.filter;

import java.util.ArrayList;
import java.util.List;

public final class Filter {

    private int sortColumnIndex = -1;
    private boolean sortAscending = true;

    private Integer limit;
    private Integer offset;

    private FilterLogic whereOperator = FilterLogic.AND;
    private final List<FilterCondition> conditions = new ArrayList<>();

    private final List<Integer> groupByColumns = new ArrayList<>();

    private final List<FilterCondition> havingConditions = new ArrayList<>();

    private final List<FilterJoin> joins = new ArrayList<>();

    private final List<Integer> selectedColumns = new ArrayList<>();

    public Filter() {

    }

    public static Filter sort(int columnIndex, boolean ascending) {
        Filter filter = new Filter();
        filter.sortColumnIndex = columnIndex;
        filter.sortAscending = ascending;
        return filter;
    }

    public static Filter search(int columnIndex, String searchTerm) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.LIKE, searchTerm));
        return filter;
    }

    public static Filter topN(int columnIndex, int n) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.GREATER, String.valueOf(n)));
        return filter;
    }

    public static Filter bottomN(int columnIndex, int n) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.LESS, String.valueOf(n)));
        return filter;
    }

    public static Filter byRange(int columnIndex, double minValue, double maxValue) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.GREATER_EQUAL, String.valueOf(minValue)));
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.LESS_EQUAL, String.valueOf(maxValue)));
        return filter;
    }

    public static Filter showEmpty(int columnIndex) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.IS_NULL, null));
        return filter;
    }

    public static Filter hideEmpty(int columnIndex) {
        Filter filter = new Filter();
        filter.conditions.add(new FilterCondition(columnIndex, FilterOperator.NOT_NULL, null));
        return filter;
    }

    public boolean hasRowLimitingEffect() {
        return conditions.stream().anyMatch(c -> switch (c.operator()) {
            case GREATER, GREATER_EQUAL, LESS, LESS_EQUAL, LIKE -> true;
            case IS_NULL, NOT_NULL -> true;
            default -> false;
        });
    }

    public boolean hasColumnFilter(int columnIndex) {
        return sortColumnIndex == columnIndex || conditions.stream().anyMatch(c -> c.columnIndex() == columnIndex);
    }
}
