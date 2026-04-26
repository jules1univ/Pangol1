package fr.univrennes.istic.l2gen.application.core.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import fr.univrennes.istic.l2gen.application.core.config.Log;
import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.filter.FilterCondition;
import fr.univrennes.istic.l2gen.application.core.filter.FilterLogic;
import fr.univrennes.istic.l2gen.application.core.filter.FilterOperator;
import fr.univrennes.istic.l2gen.application.core.filter.FilterSort;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;

public final class FilterService {

    public static List<Filter> load(File tablePath) {
        File filterPath = new File(tablePath.getParentFile(), tablePath.getName() + ".filters");
        if (!filterPath.exists()) {
            return new ArrayList<>();
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(filterPath))) {
            int filterCount = in.readInt();
            List<Filter> filters = new ArrayList<>(filterCount);
            for (int i = 0; i < filterCount; i++) {
                int columnIndex = in.readInt();
                FilterLogic operator = FilterLogic.valueOf(in.readUTF());
                FilterSort sort = FilterSort.valueOf(in.readUTF());

                Filter filter = new Filter(columnIndex);
                filter.setOperator(operator);
                filter.setSort(sort);

                int conditionCount = in.readInt();
                for (int j = 0; j < conditionCount; j++) {
                    FilterOperator conditionOperator = FilterOperator.valueOf(in.readUTF());
                    String conditionValue = in.readUTF();
                    filter.add(new FilterCondition(conditionOperator, conditionValue));
                }
                filters.add(filter);
            }
            return filters;
        } catch (Exception e) {
            Log.debug("Failed to load filters for table " + tablePath.getName(), e);
        }

        return new ArrayList<>();
    }

    public static void save(DataTable table) {
        File tablePath = table.getPath();
        File filterPath = new File(tablePath.getParentFile(), tablePath.getName() + ".filters");

        List<Filter> filters = table.getFilters();
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filterPath))) {
            out.writeInt(filters.size());
            for (Filter filter : filters) {
                out.writeInt(filter.getColumnIndex());
                out.writeUTF(filter.getOperator().name());
                out.writeUTF(filter.getSort().name());

                out.writeInt(filter.getConditions().size());
                for (var condition : filter.getConditions()) {
                    out.writeUTF(condition.operator().name());
                    if (condition.value() != null) {
                        out.writeUTF(condition.value());
                    } else {
                        out.writeUTF("");
                    }
                }
            }
        } catch (Exception e) {
            Log.debug("Failed to save filters for table " + tablePath.getName(), e);
        }
    }
}
