package fr.univrennes.istic.l2gen.application.gui.main;

import javax.swing.*;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;

import java.awt.*;

public class SplashScreen extends JWindow {

    private JProgressBar progressBar;
    private JLabel status;

    public SplashScreen() {

        setSize(500, 300);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/icons/logo.png"));
        JLabel background = new JLabel(backgroundImage);
        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel overlay = new JPanel();
        overlay.setLayout(new BorderLayout());
        overlay.setOpaque(false);
        overlay.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel title = new JLabel(Lang.get("app.loading"), SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        status = new JLabel("", SwingConstants.CENTER);
        status.setForeground(Color.WHITE);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(Color.WHITE);
        progressBar.setBackground(new Color(255, 255, 255, 80));
        progressBar.setBorderPainted(false);

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.setOpaque(false);
        bottom.add(status, BorderLayout.NORTH);
        bottom.add(progressBar, BorderLayout.SOUTH);

        overlay.add(title, BorderLayout.NORTH);
        overlay.add(bottom, BorderLayout.SOUTH);

        background.add(overlay);
    }

    public void setStatus(String text) {
        status.setText(text);
    }
}