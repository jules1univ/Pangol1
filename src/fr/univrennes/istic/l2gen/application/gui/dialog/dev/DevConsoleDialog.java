package fr.univrennes.istic.l2gen.application.gui.dialog.dev;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.dialog.DialogBase;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class DevConsoleDialog extends JDialog {

    private final JTextArea consoleArea = new JTextArea();

    private final JPanel builderPanel = new JPanel();

    private final JComboBox<Command> rootSelector = new JComboBox<>();

    private final Map<String, Object> values = new LinkedHashMap<>();

    public static void show(Frame parent) {
        new DevConsoleDialog(parent).setVisible(true);
    }

    public DevConsoleDialog(Frame parent) {
        super(parent, Lang.get("devconsole.title"), true);

        setSize(DialogBase.WIDTH, DialogBase.HEIGHT);
        setMinimumSize(new Dimension(DialogBase.WIDTH, DialogBase.HEIGHT));
        setLocationRelativeTo(parent);

        build();
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout());
        consoleArea.setEditable(false);
        root.add(new JScrollPane(consoleArea), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        builderPanel.setLayout(new BoxLayout(builderPanel, BoxLayout.Y_AXIS));

        for (Command command : CommandRegistry.getCommands()) {
            rootSelector.addItem(command);
        }
        rootSelector.addActionListener(e -> rebuildForm());

        JPanel topLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topLine.add(new JLabel(Lang.get("devconsole.command")));
        topLine.add(rootSelector);
        builderPanel.add(topLine);
        bottom.add(builderPanel, BorderLayout.CENTER);

        JButton execute = new JButton(Lang.get("devconsole.send"));
        execute.addActionListener(e -> execute());
        bottom.add(execute, BorderLayout.EAST);
        root.add(bottom, BorderLayout.SOUTH);

        setContentPane(root);
        rebuildForm();
    }

    private void rebuildForm() {
        while (builderPanel.getComponentCount() > 1) {
            builderPanel.remove(1);
        }
        values.clear();

        Command rootCommand = (Command) rootSelector.getSelectedItem();
        if (rootCommand != null) {
            buildNode(rootCommand, 0);
        }

        builderPanel.revalidate();
        builderPanel.repaint();
    }

    private void buildNode(Command node, int depth) {
        for (Command child : node.children()) {
            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
            line.setBorder(BorderFactory.createEmptyBorder(
                    0,
                    depth * 20,
                    0,
                    0));
            line.add(new JLabel(child.label()));

            switch (child.type()) {
                case INPUT -> {
                    JTextField field = new JTextField(15);
                    field.getDocument().addDocumentListener(
                            new DocumentListener() {
                                @Override
                                public void insertUpdate(javax.swing.event.DocumentEvent e) {
                                    values.put(child.id(), field.getText());
                                }

                                @Override
                                public void removeUpdate(javax.swing.event.DocumentEvent e) {
                                    values.put(child.id(), field.getText());
                                }

                                @Override
                                public void changedUpdate(javax.swing.event.DocumentEvent e) {
                                    values.put(child.id(), field.getText());
                                }
                            });
                    line.add(field);
                    builderPanel.add(line);
                }
                case SELECT -> {
                    JComboBox<Command> combo = new JComboBox<>();
                    for (Command option : child.children()) {
                        combo.addItem(option);
                    }
                    combo.addActionListener(e -> {
                        rebuildForm();
                        Command selected = (Command) combo.getSelectedItem();
                        if (selected != null) {
                            values.put(child.id(), selected.id());
                        }
                    });

                    line.add(combo);
                    builderPanel.add(line);

                    Command selected = (Command) combo.getSelectedItem();
                    if (selected != null) {
                        buildNode(selected, depth + 1);
                    }
                }
                default -> {
                }
            }
        }
    }

    private void execute() {
        Command command = (Command) rootSelector.getSelectedItem();
        if (command == null) {
            return;
        }
        consoleArea.append("> " + command.id() + " " + values + "\n");
        if (command.executor() != null && values != null && !values.isEmpty()) {
            String result = command.executor().execute(values);
            if (result != null && !result.isEmpty()) {
                consoleArea.append(result + "\n");
            }
        }
    }
}