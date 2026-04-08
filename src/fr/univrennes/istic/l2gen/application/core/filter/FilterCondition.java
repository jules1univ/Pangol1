package fr.univrennes.istic.l2gen.application.core.filter;

public record FilterCondition(int columnIndex, FilterOperator operator, String value) {
}