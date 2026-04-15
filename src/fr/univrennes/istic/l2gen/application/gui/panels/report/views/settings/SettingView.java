package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.univrennes.istic.l2gen.application.core.icon.Ico;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.ChartSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.DataSettingsPanel;

public class SettingView extends JPanel {

    public SettingView() {
        this.build();
    }

    private void build() {
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));

        JButton addChartButton = new JButton(Ico.get("icons/add.png"));
        addChartButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, addChartButton.getPreferredSize().height));
        addChartButton.setAlignmentX(CENTER_ALIGNMENT);

        content.add(addChartButton);
        content.add(Box.createVerticalStrut(10));

        content.add(new ChartSettingsPanel());
        content.add(new DataSettingsPanel());

        content.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    public void refresh() {
        removeAll();
        build();
        revalidate();
        repaint();
    }
}
