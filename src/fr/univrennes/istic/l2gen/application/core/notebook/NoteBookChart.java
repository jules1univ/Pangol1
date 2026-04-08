package fr.univrennes.istic.l2gen.application.core.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Optional;

import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.visustats.data.DataGroup;

public final class NoteBookChart implements NoteBookValue {
    private DataTable table;
    private DataGroup group;

    private Optional<Integer> nameIndex;
    private int valueIndex;

    private boolean includeFilters;

    public NoteBookChart(DataTable table) {
        this.table = table;
        this.group = new DataGroup(null);

        this.nameIndex = Optional.empty();
        this.valueIndex = -1;
        this.includeFilters = false;
    }

    @Override
    public void exportHTML(StringBuilder html) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exportHTML'");
    }

    @Override
    public void save(DataOutputStream out) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void load(DataInputStream in) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load'");
    }
}
