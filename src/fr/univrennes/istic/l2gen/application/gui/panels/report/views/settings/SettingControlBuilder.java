package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public final class SettingControlBuilder {

    private SettingControlBuilder() {
    }

    public static JTextField textField(String def) {
        return new JTextField(def);
    }

    public static JComboBox<String> dropdown(String[] options) {
        return new JComboBox<>(options);
    }

    public static JSpinner spinner(int min, int max, int value, int step) {
        return new JSpinner(new SpinnerNumberModel(value, min, max, step));
    }

    public static JCheckBox checkbox(boolean selected) {
        JCheckBox box = new JCheckBox();
        box.setSelected(selected);
        return box;
    }
}
