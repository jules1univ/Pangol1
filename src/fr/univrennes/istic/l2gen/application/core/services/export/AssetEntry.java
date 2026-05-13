package fr.univrennes.istic.l2gen.application.core.services.export;

import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;

public final class AssetEntry {
    private final NoteBookValue value;
    private String imagePath;
    private String chartPath;
    private String chartImagePath;

    public AssetEntry(NoteBookValue value) {
        this.value = value;
    }

    public NoteBookValue getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getChartPath() {
        return chartPath;
    }

    public void setChartPath(String chartPath) {
        this.chartPath = chartPath;
    }

    public String getChartImagePath() {
        return chartImagePath;
    }

    public void setChartImagePath(String chartImagePath) {
        this.chartImagePath = chartImagePath;
    }
}