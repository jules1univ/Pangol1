package fr.univrennes.istic.l2gen.application.core.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;

public final class NoteBookService {
    private static final List<NoteBookValue> values = new ArrayList<>();

    public static void add(NoteBookValue value) {
        values.add(value);
    }

    public static void update(int index, NoteBookValue value) {
        if (index < 0 || index >= values.size()) {
            return;
        }
        values.set(index, value);
    }

    public static List<NoteBookValue> getValues() {
        return Collections.unmodifiableList(values);
    }

    public static void remove(int index) {
        if (index < 0 || index >= values.size()) {
            return;
        }
        values.remove(index);
    }

    public static void move(int fromIndex, int toIndex) {
        if (fromIndex < 0 || fromIndex >= values.size()) {
            return;
        }
        if (toIndex < 0 || toIndex >= values.size()) {
            return;
        }
        if (fromIndex == toIndex) {
            return;
        }
        NoteBookValue value = values.remove(fromIndex);
        if (toIndex > fromIndex) {
            toIndex--;
        }
        values.add(toIndex, value);
    }

}
