package fr.univrennes.istic.l2gen.application.gui.dialog;

import fr.univrennes.istic.l2gen.application.core.filter.Filter;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;

import javax.swing.*;
import java.awt.*;

public class FilterDialog extends JDialog {

    private Filter filter;

    public FilterDialog(Frame parent) {
        super(parent, Lang.get("filterdiag.title"), true);
        build();
    }

    private void build() {
        setSize(700, 400);
        setLayout(new BorderLayout());

    }

}