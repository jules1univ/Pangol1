package fr.univrennes.istic.l2gen.application.core.notebook;

public final class NoteBookText implements NoteBookValue {
    private String text;
    private String rtf;

    public NoteBookText(String text) {
        this.text = text;
        this.rtf = null;
    }

    public NoteBookText(String text, String rtf) {
        this.text = text;
        this.rtf = rtf;
    }

    public String getText() {
        return text;
    }

    public String getRtf() {
        return rtf;
    }

    @Override
    public void exportHTML(StringBuilder html) {
        html.append("<p>").append(text).append("</p>");
    }

}
