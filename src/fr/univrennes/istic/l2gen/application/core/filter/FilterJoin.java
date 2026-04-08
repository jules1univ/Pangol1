package fr.univrennes.istic.l2gen.application.core.filter;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;

public record FilterJoin(DataTable other, int targetColumnIndex, int otherColumnIndex, FilterJoinType type) {
}
