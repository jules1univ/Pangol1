package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fr.univrennes.istic.l2gen.application.core.icon.Ico;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.ChartSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.DataSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.ImageSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.TextSettingsPanel;

public class SettingView extends JPanel {

    private static final String CARD_BASE = "base";
    private static final String CARD_CHART = "chart";
    private static final String CARD_IMAGE = "image";
    private static final String CARD_TEXT = "text";

    private final CardLayout cardLayout = new CardLayout();

    public SettingView() {
        build();
    }

    private void build() {
        setLayout(cardLayout);

        add(buildBasePanel(), CARD_BASE);
        add(buildChartSettingsPanel(), CARD_CHART);
        add(buildImageSettingsPanel(), CARD_IMAGE);
        add(buildTextSettingsPanel(), CARD_TEXT);

        cardLayout.show(this, CARD_BASE);
    }

    private JPanel buildBasePanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));

        JButton addChartButton = buildActionButton(Lang.get("report.add.chart"), "icons/add_chart.svg");
        addChartButton.addActionListener(e -> cardLayout.show(this, CARD_CHART));
        content.add(addChartButton);

        content.add(Box.createVerticalStrut(6));

        JButton addImageButton = buildActionButton(Lang.get("report.add.image"), "icons/add_image.svg");
        addImageButton.addActionListener(e -> cardLayout.show(this, CARD_IMAGE));
        content.add(addImageButton);

        content.add(Box.createVerticalStrut(6));

        JButton addTextButton = buildActionButton(Lang.get("report.add.text"), "icons/add_text.svg");
        addTextButton.addActionListener(e -> cardLayout.show(this, CARD_TEXT));
        content.add(addTextButton);

        content.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel basePanel = new JPanel(new BorderLayout());
        basePanel.add(scroll, BorderLayout.CENTER);
        return basePanel;
    }

    private JPanel buildChartSettingsPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));
        content.add(buildBackAndNextButtons());
        content.add(Box.createVerticalStrut(10));

        content.add(new ChartSettingsPanel());
        content.add(new DataSettingsPanel());

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JPanel buildImageSettingsPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));
        content.add(buildBackAndNextButtons());
        content.add(Box.createVerticalStrut(10));

        content.add(new ImageSettingsPanel());

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JPanel buildTextSettingsPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));
        content.add(buildBackAndNextButtons());
        content.add(Box.createVerticalStrut(10));

        content.add(new TextSettingsPanel());

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JButton buildActionButton(String label, String iconPath) {
        JButton button = new JButton(label, Ico.get(iconPath));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height * 2));
        button.setAlignmentX(CENTER_ALIGNMENT);
        return button;
    }

    private JPanel buildBackAndNextButtons() {
        JButton backButton = new JButton(Lang.get("report.back"));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> cardLayout.show(this, CARD_BASE));

        JButton nextButton = new JButton(Lang.get("report.next"));
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> cardLayout.show(this, CARD_BASE));

        JPanel panel = new JPanel();
        panel.add(backButton);
        panel.add(nextButton);
        return panel;
    }

    private JPanel wrapInScroll(JPanel content) {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    public void refresh() {
        removeAll();
        build();
        revalidate();
        repaint();
    }
}
