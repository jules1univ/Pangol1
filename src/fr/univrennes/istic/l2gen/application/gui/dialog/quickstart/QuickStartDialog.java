package fr.univrennes.istic.l2gen.application.gui.dialog.quickstart;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.univrennes.istic.l2gen.application.core.config.Lang;
import fr.univrennes.istic.l2gen.application.gui.main.MainView;

public final class QuickStartDialog extends JDialog {

    private final Runnable onLaunch;

    public static void showDialog(MainView parent, Runnable onLaunch) {
        if (parent == null) {
            return;
        }

        Runnable show = () -> {
            Frame frame = parent instanceof Frame ? (Frame) parent : null;
            QuickStartDialog dialog = new QuickStartDialog(frame, onLaunch);
            dialog.setVisible(true);
        };

        if (SwingUtilities.isEventDispatchThread()) {
            show.run();
        } else {
            SwingUtilities.invokeLater(show);
        }
    }

    private QuickStartDialog(Frame parent, Runnable onLaunch) {
        super(parent, Lang.get("quickstart.dialog.title"), true);
        this.onLaunch = onLaunch;
        build();
        pack();
        setLocationRelativeTo(parent);
    }

    private void build() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel message = new JLabel(toHtml(Lang.get("quickstart.dialog.message")));
        root.add(message, BorderLayout.CENTER);

        JButton startButton = new JButton(Lang.get("quickstart.dialog.start"));
        JButton laterButton = new JButton(Lang.get("quickstart.dialog.later"));

        startButton.addActionListener(event -> {
            dispose();
            if (onLaunch != null) {
                onLaunch.run();
            }
        });
        laterButton.addActionListener(event -> dispose());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        footer.add(laterButton);
        footer.add(startButton);

        root.add(footer, BorderLayout.SOUTH);
        setContentPane(root);

        setMinimumSize(new Dimension(420, getPreferredSize().height));
        setResizable(false);
        getRootPane().setDefaultButton(startButton);
    }

    private String toHtml(String text) {
        if (text == null) {
            return "";
        }
        String escaped = text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br>");
        return "<html><div style='width:360px;'>" + escaped + "</div></html>";
    }
}
