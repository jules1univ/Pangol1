package fr.univrennes.istic.l2gen.application.gui.dialog.filter;

enum FilterType {
    SEARCH("Search (LIKE)"),
    RANGE("Range (between)"),
    TOP_N("Top N (greater than)"),
    BOTTOM_N("Bottom N (less than)"),
    SHOW_EMPTY("Show empty"),
    HIDE_EMPTY("Hide empty"),
    SORT("Sort only");

    private final String label;

    FilterType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}