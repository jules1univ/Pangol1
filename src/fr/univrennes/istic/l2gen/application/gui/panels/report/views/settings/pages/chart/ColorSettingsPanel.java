package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.chart;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JPanel;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingRowPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.SettingSectionPanel;
import fr.univrennes.istic.l2gen.svg.color.Color;

public final class ColorSettingsPanel extends SettingSectionPanel {
    private static final int SWATCH_SIZE = 18;

    private Map<String, Color> colorMap = new LinkedHashMap<>();

    public ColorSettingsPanel() {
        super(Lang.get("report.settings.chart.colors"));
        build();
    }

    private void build() {
        for (Map.Entry<String, Color> entry : colorMap.entrySet()) {
            String label = entry.getKey();
            Color color = entry.getValue();
            addRow(new SettingRowPanel(label, createColorControl(label, color)));
        }

        setVisible(colorMap.size() > 0);
    }

    private JComponent createColorControl(String label, Color color) {
        JPanel control = new JPanel();
        control.setOpaque(false);
        control.setLayout(new BoxLayout(control, BoxLayout.X_AXIS));

        JPanel swatch = createSwatch(color);

        JButton chooseColorButton = new JButton(Lang.get("report.settings.chart.select_color") + "...");
        chooseColorButton.addActionListener(e -> {
            java.awt.Color selectedColor = JColorChooser.showDialog(
                    this,
                    Lang.get("report.settings.chart.colors"),
                    swatch.getBackground());

            if (selectedColor == null) {
                return;
            }

            Color newColor = Color.fromAWT(selectedColor);
            colorMap.put(label, newColor);
            swatch.setBackground(selectedColor);
            swatch.repaint();
        });

        control.add(chooseColorButton);
        control.add(Box.createHorizontalStrut(8));
        control.add(swatch);
        control.add(Box.createHorizontalGlue());
        return control;
    }

    private JPanel createSwatch(Color color) {
        JPanel swatch = new JPanel();
        swatch.setOpaque(true);
        swatch.setBackground(color.toAWT());
        swatch.setPreferredSize(new Dimension(SWATCH_SIZE, SWATCH_SIZE));
        swatch.setMinimumSize(new Dimension(SWATCH_SIZE, SWATCH_SIZE));
        swatch.setMaximumSize(new Dimension(SWATCH_SIZE, SWATCH_SIZE));
        swatch.setBorder(BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY));
        return swatch;
    }

    public void setColorMap(Map<String, Color> newColorMap) {
        this.colorMap = newColorMap != null ? newColorMap : new HashMap<>();
        clearRows();
        build();
        revalidate();
        repaint();
    }

    public Map<String, Color> getColorMap() {
        return new HashMap<>(colorMap);
    }

}
