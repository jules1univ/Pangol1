package fr.univrennes.istic.l2gen.application.core.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public sealed interface NoteBookValue permits NoteBookText, NoteBookImage, NoteBookChart {

    public void exportHTML(StringBuilder html);

    public void save(DataOutputStream out);

    public void load(DataInputStream in);
}