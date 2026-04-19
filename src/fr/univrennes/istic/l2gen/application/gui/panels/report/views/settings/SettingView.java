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
import fr.univrennes.istic.l2gen.application.core.notebook.NoteBookValue;
import fr.univrennes.istic.l2gen.application.core.services.NoteBookService;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.ChartSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.DataSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.ImageSettingsPanel;
import fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings.pages.TextSettingsPanel;

public class SettingView extends JPanel {

    private final CardLayout cardLayout = new CardLayout();

    private ChartSettingsPanel chartSettingsPanel;
    private DataSettingsPanel dataSettingsPanel;

    private ImageSettingsPanel imageSettingsPanel;
    private TextSettingsPanel textSettingsPanel;

    public SettingView() {
        build();
    }

    private void build() {
        setLayout(cardLayout);

        add(buildBasePanel(), SettingViewType.BASE.name());
        add(buildChartSettingsPanel(), SettingViewType.CHART.name());
        add(buildImageSettingsPanel(), SettingViewType.IMAGE.name());
        add(buildTextSettingsPanel(), SettingViewType.TEXT.name());

        showCard(SettingViewType.BASE);
    }

    private JPanel buildBasePanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));

        JButton addChartButton = buildActionButton(Lang.get("report.add.chart"), "icons/add_chart.svg");
        addChartButton.addActionListener(e -> showCard(SettingViewType.CHART));
        content.add(addChartButton);

        content.add(Box.createVerticalStrut(6));

        JButton addImageButton = buildActionButton(Lang.get("report.add.image"), "icons/add_image.svg");
        addImageButton.addActionListener(e -> showCard(SettingViewType.IMAGE));
        content.add(addImageButton);

        content.add(Box.createVerticalStrut(6));

        JButton addTextButton = buildActionButton(Lang.get("report.add.text"), "icons/add_text.svg");
        addTextButton.addActionListener(e -> showCard(SettingViewType.TEXT));
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
        content.add(buildBackAndNextButtons(SettingViewType.CHART));
        content.add(Box.createVerticalStrut(10));

        chartSettingsPanel = new ChartSettingsPanel();
        dataSettingsPanel = new DataSettingsPanel();
        content.add(chartSettingsPanel);
        content.add(dataSettingsPanel);

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JPanel buildImageSettingsPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));
        content.add(buildBackAndNextButtons(SettingViewType.IMAGE));
        content.add(Box.createVerticalStrut(10));

        imageSettingsPanel = new ImageSettingsPanel();
        content.add(imageSettingsPanel);

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JPanel buildTextSettingsPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createVerticalStrut(5));
        content.add(buildBackAndNextButtons(SettingViewType.TEXT));
        content.add(Box.createVerticalStrut(10));

        textSettingsPanel = new TextSettingsPanel();
        content.add(textSettingsPanel);

        content.add(Box.createVerticalGlue());

        return wrapInScroll(content);
    }

    private JButton buildActionButton(String label, String iconPath) {
        JButton button = new JButton(label, Ico.get(iconPath));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height * 2));
        button.setAlignmentX(CENTER_ALIGNMENT);
        return button;
    }

    private JPanel buildBackAndNextButtons(SettingViewType targetCard) {
        JButton backButton = new JButton(Lang.get("report.back"));
        backButton.setPreferredSize(new Dimension(backButton.getPreferredSize().width, 24));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.addActionListener(e -> showCard(SettingViewType.BASE));

        JButton nextButton = new JButton(Lang.get("report.next"));
        nextButton.setIcon(Ico.get("icons/add.svg"));
        nextButton.setPreferredSize(new Dimension(nextButton.getPreferredSize().width, 24));
        nextButton.setAlignmentX(CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> {
            handleNext(targetCard);
            showCard(SettingViewType.BASE);
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(backButton, BorderLayout.WEST);
        panel.add(nextButton, BorderLayout.EAST);
        return panel;
    }

    private void handleNext(SettingViewType type) {
        NoteBookValue value = null;
        switch (type) {
            case CHART:
                if (dataSettingsPanel != null && chartSettingsPanel != null) {
                    chartSettingsPanel.setDataSettings(dataSettingsPanel);
                    value = chartSettingsPanel.createNoteBook();
                }
                break;
            case IMAGE:
                if (imageSettingsPanel != null) {
                    value = imageSettingsPanel.createNoteBook();
                }
                break;
            case TEXT:
                if (textSettingsPanel != null) {
                    value = textSettingsPanel.createNoteBook();
                }
                break;
            default:
                break;
        }
        if (value != null) {
            NoteBookService.add(value);
        }
    }

    private void showCard(SettingViewType card) {
        cardLayout.show(this, card.name());
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
