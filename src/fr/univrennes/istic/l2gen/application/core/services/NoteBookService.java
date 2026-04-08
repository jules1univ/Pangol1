package fr.univrennes.istic.l2gen.application.core.services;

import java.util.List;
import java.util.ArrayList;

import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;

public final class NoteBookService {
    private static final List<NoteBookValue> values = new ArrayList<>();

    public static void add(NoteBookValue value) {
        values.add(value);
    }

    public static void move(int index, boolean up) {
        if (index < 0 || index >= values.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        if (up) {
            if (index == 0) {
                return;
            }
            NoteBookValue temp = values.get(index - 1);
            values.set(index - 1, values.get(index));
            values.set(index, temp);
        } else {
            if (index == values.size() - 1) {
                return;
            }
            NoteBookValue temp = values.get(index + 1);
            values.set(index + 1, values.get(index));
            values.set(index, temp);
        }
    }

}
