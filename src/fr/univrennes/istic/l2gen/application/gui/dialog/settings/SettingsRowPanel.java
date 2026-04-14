package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public final class SettingsRowPanel extends JPanel {

    public static final int ROW_HEIGHT = 32;
    public static final int LABEL_WIDTH = 220;

    public SettingsRowPanel(String labelText, Component control) {
        this(labelText, new Component[] { control });
    }

    public SettingsRowPanel(String labelText, Component... controls) {
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(2, 12, 2, 12));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, ROW_HEIGHT));
        setPreferredSize(new Dimension(0, ROW_HEIGHT));

        if (labelText != null) {

            JLabel label = new JLabel(labelText);
            label.setFont(label.getFont().deriveFont(Font.PLAIN, 12f));
            label.setForeground(UIManager.getColor("Label.foreground"));
            label.setPreferredSize(new Dimension(LABEL_WIDTH, ROW_HEIGHT));

            add(label, BorderLayout.WEST);
        }

        JPanel controlsPanel = new JPanel();
        controlsPanel.setOpaque(false);
        controlsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        for (Component control : controls) {
            controlsPanel.add(control);
        }

        add(controlsPanel, BorderLayout.CENTER);
    }
}
