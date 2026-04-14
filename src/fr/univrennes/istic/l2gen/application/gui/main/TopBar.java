package fr.univrennes.istic.l2gen.application.gui.main;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.services.TableService;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.gui.GUIController;
import fr.univrennes.istic.l2gen.application.gui.dialog.settings.SettingsDialog;

public final class TopBar extends JMenuBar {

    public TopBar() {
        add(buildFileMenu());
        add(buildViewMenu());
        add(buildHelpMenu());
    }

    private JMenu buildFileMenu() {
        JMenu fileMenu = new JMenu(Lang.get("menu.file"));
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem openItem = new JMenuItem(Lang.get("menu.file.open"));
        openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        openItem.addActionListener(event -> GUIController.getInstance().onOpenFileDialog());
        fileMenu.add(openItem);

        if (!TableService.getRecentTables().isEmpty()) {
            JMenu openRecentMenu = new JMenu(Lang.get("menu.file.open_recent"));
            for (File recentFile : TableService.getRecentTables()) {
                JMenuItem recentItem = new JMenuItem(recentFile.getAbsolutePath());
                recentItem.addActionListener(event -> {
                    DataTable table = TableService.get(recentFile);
                    if (!recentFile.exists() || table == null) {
                        TableService.removeRecent(recentFile);
                        GUIController.getInstance().onOpenExceptionDialog(
                                new IOException(Lang.get("error.table_not_found", recentFile.getAbsolutePath())));
                    } else {
                        GUIController.getInstance().setTable(table);
                    }
                });
                openRecentMenu.add(recentItem);
            }
            fileMenu.add(openRecentMenu);
        }

        JMenuItem openUrlItem = new JMenuItem(Lang.get("menu.file.open_url"));
        openUrlItem.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        openUrlItem.addActionListener(event -> GUIController.getInstance().onOpenUrlDialog());
        fileMenu.add(openUrlItem);

        fileMenu.addSeparator();

        JMenuItem settingsItem = new JMenuItem(Lang.get("menu.file.settings"));
        settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, KeyEvent.CTRL_DOWN_MASK));
        settingsItem.addActionListener(event -> {
            Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            new SettingsDialog(parentFrame).setVisible(true);
        });
        fileMenu.add(settingsItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem(Lang.get("menu.file.exit"));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
        exitItem.addActionListener(event -> System.exit(0));
        fileMenu.add(exitItem);

        return fileMenu;
    }

    private JMenu buildViewMenu() {
        JMenu viewMenu = new JMenu(Lang.get("menu.view"));
        viewMenu.setMnemonic(KeyEvent.VK_V);

        JMenu panelsMenu = new JMenu(Lang.get("menu.view.panels"));
        panelsMenu.addSeparator();
        viewMenu.add(panelsMenu);

        return viewMenu;
    }

    private JMenu buildHelpMenu() {
        JMenu helpMenu = new JMenu(Lang.get("menu.help"));
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenu languagesMenu = new JMenu(Lang.get("menu.help.languages"));

        for (String lang : Lang.getSupportedLanguages()) {
            Locale local = Locale.forLanguageTag(lang);

            String displayName = local.getDisplayLanguage(local);
            displayName = displayName.substring(0, 1).toUpperCase() + displayName.substring(1);

            JMenuItem languageItem = new JMenuItem(displayName + " (" + local.getLanguage().toUpperCase() + ")");
            languageItem.addActionListener(event -> GUIController.getInstance().onLanguageChange(local));
            languagesMenu.add(languageItem);
        }

        helpMenu.add(languagesMenu);

        helpMenu.addSeparator();

        JMenuItem documentationItem = new JMenuItem(Lang.get("menu.help.documentation"));
        documentationItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        documentationItem.addActionListener(event -> GUIController.getInstance().onOpenDocDialog());
        helpMenu.add(documentationItem);

        JMenuItem aboutItem = new JMenuItem(Lang.get("menu.help.about"));
        aboutItem.addActionListener(event -> GUIController.getInstance().onOpenAboutDialog());
        helpMenu.add(aboutItem);

        return helpMenu;
    }
}