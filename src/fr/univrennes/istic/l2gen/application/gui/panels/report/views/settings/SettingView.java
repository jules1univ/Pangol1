package fr.univrennes.istic.l2gen.application.gui.panels.report.views.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.gui.GUIController;

public class SettingView extends JPanel {
    private final GUIController controller;

    public SettingView(GUIController controller) {
        this.controller = controller;
        this.build();
    }

    private void build() {
        setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(buildSection(Lang.get("setting.chart"), new JPanel[][] {
                row(Lang.get("setting.chart.type"),
                        dropdown(new String[] { Lang.get("setting.chart.pie"), Lang.get("setting.chart.bar"),
                                Lang.get("setting.chart.columns") })),
                row(Lang.get("setting.chart.title"), textField(Lang.get("setting.chart.default_title"))),
                separator(),
                row(Lang.get("setting.chart.show_legend"), checkbox(true)),
                separator(Lang.get("setting.chart.axis")),
                row(Lang.get("setting.chart.tick_count"), spinner(1, 50, 5, 1)),
                row(Lang.get("setting.chart.scale"),
                        dropdown(new String[] { Lang.get("setting.chart.linear"), Lang.get("setting.chart.logarithmic"),
                                Lang.get("setting.chart.sqrt") })),
                row(Lang.get("setting.chart.show_x_axis"), checkbox(true)),
                row(Lang.get("setting.chart.x_label"), textField(Lang.get("setting.chart.default_labelx"))),
                row(Lang.get("setting.chart.show_y_axis"), checkbox(true)),
                row(Lang.get("setting.chart.y_label"), textField(Lang.get("setting.chart.default_labely"))),

        }));

        String[] cols = controller.getTable().map(table -> {
            final int size = (int) table.getColumnCount();
            String[] names = new String[size];
            for (int i = 0; i < size; i++) {
                names[i] = table.getColumnName(i);
            }
            return names;
        }).orElse(new String[] {});

        String[] colsWithNone = new String[cols.length + 1];
        System.arraycopy(cols, 0, colsWithNone, 0, cols.length);
        colsWithNone[cols.length] = Lang.get("setting.data.none");

        content.add(buildSection(Lang.get("setting.data"), new JPanel[][] {
                row(Lang.get("setting.data.col_group"), dropdown(colsWithNone)),
                row(Lang.get("setting.data.col_value"), dropdown(cols)),
                separator(),
                row(Lang.get("setting.data.filter_include"), checkbox(true))
        }));

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

    private JPanel buildSection(String title, JPanel[][] rows) {
        JPanel section = new JPanel(new BorderLayout());

        JPanel body = new JPanel(new GridBagLayout());
        body.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(1, 2, 1, 2);
        gbc.gridy = 0;

        for (JPanel[] row : rows) {
            gbc.gridx = 0;
            gbc.weightx = 0.4;
            body.add(row[0], gbc);
            gbc.gridx = 1;
            gbc.weightx = 0.6;
            body.add(row[1], gbc);
            gbc.gridy++;
        }

        Icon expandedIcon = UIManager.getIcon("Tree.expandedIcon");
        Icon collapsedIcon = UIManager.getIcon("Tree.collapsedIcon");

        JButton header = new JButton(title, expandedIcon);
        header.setHorizontalAlignment(SwingConstants.LEFT);
        header.setFocusPainted(true);
        header.setBorderPainted(true);
        header.setContentAreaFilled(true);

        header.addActionListener(e -> {
            boolean visible = !body.isVisible();
            body.setVisible(visible);
            header.setIcon(visible ? expandedIcon : collapsedIcon);
        });

        section.add(header, BorderLayout.NORTH);
        section.add(body, BorderLayout.CENTER);
        section.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return section;
    }

    private JPanel[] row(String label, JComponent control) {
        JPanel lp = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        lp.add(new JLabel(label));

        JPanel cp = new JPanel(new BorderLayout());
        cp.add(control, BorderLayout.CENTER);

        return new JPanel[] { lp, cp };
    }

    private JTextField textField(String def) {
        return new JTextField(def);
    }

    private JComboBox<String> dropdown(String[] options) {
        return new JComboBox<>(options);
    }

    private JPanel[] separator() {
        return separator(null);
    }

    private JPanel[] separator(String label) {
        JPanel sep = new JPanel() {
            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                int y = getHeight() / 2;
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, y, getWidth(), y);
            }
        };
        sep.setPreferredSize(new Dimension(0, 12));

        if (label != null) {
            JLabel lbl = new JLabel(label);
            lbl.setFont(lbl.getFont().deriveFont(Font.ITALIC));
            lbl.setForeground(Color.GRAY);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.add(lbl, BorderLayout.WEST);
            wrapper.add(sep, BorderLayout.CENTER);
            return new JPanel[] { wrapper, new JPanel() };
        } else {
            return new JPanel[] { sep, new JPanel() };
        }
    }

    private JSpinner spinner(int min, int max, int value, int step) {
        return new JSpinner(new SpinnerNumberModel(value, min, max, step));
    }

    private JCheckBox checkbox(boolean selected) {
        JCheckBox box = new JCheckBox();
        box.setSelected(selected);
        return box;
    }

    private JPanel text(String def) {
        JPanel wrapper = new JPanel(new BorderLayout());
        JTextField field = new JTextField(def);
        wrapper.add(field, BorderLayout.CENTER);
        return wrapper;
    }

    private JSlider slider(int min, int max, int value) {
        JSlider s = new JSlider(min, max, value);
        s.setPaintTicks(false);
        s.setPaintLabels(false);
        return s;
    }

    private JPanel colorPicker(Color initial) {
        JPanel wrapper = new JPanel(new BorderLayout(4, 0));
        JPanel swatch = new JPanel();
        swatch.setBackground(initial);
        swatch.setPreferredSize(new Dimension(24, 0));
        JButton btn = new JButton("Pick...");
        btn.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(this, "Choose color", swatch.getBackground());
            if (chosen != null)
                swatch.setBackground(chosen);
        });
        wrapper.add(swatch, BorderLayout.WEST);
        wrapper.add(btn, BorderLayout.CENTER);
        return wrapper;
    }

}
