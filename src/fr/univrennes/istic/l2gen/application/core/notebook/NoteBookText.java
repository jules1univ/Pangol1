package fr.univrennes.istic.l2gen.application.core.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import fr.univrennes.istic.l2gen.application.core.config.Log;

public final class NoteBookText implements NoteBookValue {

    private String text;

    public NoteBookText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void exportHTML(StringBuilder html) {
        html.append("<p>").append(text).append("</p>");
    }

    @Override
    public void save(DataOutputStream out) {
        try {
            out.writeUTF("TEXT");
            out.writeUTF(text);
        } catch (Exception e) {
            Log.debug("Failed to save text", e);
        }
    }

    @Override
    public void load(DataInputStream in) {
        try {
            this.text = in.readUTF();
        } catch (Exception e) {
            Log.debug("Failed to load text", e);
        }
    }

}
