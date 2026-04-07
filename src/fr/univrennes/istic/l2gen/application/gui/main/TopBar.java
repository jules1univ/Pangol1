package fr.univrennes.istic.l2gen.application.gui.main;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.services.TableService;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;
import fr.univrennes.istic.l2gen.application.gui.GUIController;

public final class TopBar extends JMenuBar {

    private final GUIController controller;

    public TopBar(GUIController controller) {
        this.controller = controller;
        this.build();
    }

    private void build() {
        JMenu fileMenu = new JMenu(Lang.get("menu.file"));

        JMenuItem openItem = new JMenuItem(Lang.get("menu.file.open"));
        openItem.addActionListener(e -> controller.onOpenFileDialog());
        fileMenu.add(openItem);

        if (TableService.getRecentTables().size() > 0) {

            JMenu openRecent = new JMenu(Lang.get("menu.file.open_recent"));
            fileMenu.add(openRecent);

            for (File recent : TableService.getRecentTables()) {
                JMenuItem recentItem = new JMenuItem(recent.getAbsolutePath());
                recentItem.addActionListener(e -> {
                    DataTable table = TableService.get(recent);
                    if (!recent.exists() || table == null) {
                        TableService.removeRecent(recent);
                        controller.onOpenExceptionDialog(
                                new IOException(Lang.get("error.table_not_found", recent.getAbsolutePath())));
                    } else {
                        controller.setTable(table);
                    }
                });
                openRecent.add(recentItem);
            }

        }

        fileMenu.addSeparator();

        JMenuItem openUrlItem = new JMenuItem(Lang.get("menu.file.open_url"));
        openUrlItem.addActionListener(e -> controller.onOpenUrlDialog());
        fileMenu.add(openUrlItem);

        JMenuItem exitItem = new JMenuItem(Lang.get("menu.file.exit"));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu view = new JMenu(Lang.get("menu.view"));

        JMenu panels = new JMenu(Lang.get("menu.view.panels"));
        panels.addSeparator();

        view.add(panels);

        JMenu help = new JMenu(Lang.get("menu.help"));

        Set<String> languages = new HashSet<>();
        JMenu langs = new JMenu(Lang.get("menu.help.languages"));

        for (Locale locale : Locale.getAvailableLocales()) {

            if (!Lang.isSupported(locale)) {
                continue;
            }

            if (!languages.add(locale.getLanguage())) {
                continue;
            }

            Locale langLocale = Locale.forLanguageTag(locale.getLanguage());
            String name = langLocale.getDisplayLanguage(langLocale);

            name = name.substring(0, 1).toUpperCase() + langLocale.getDisplayLanguage(langLocale).substring(1);
            JMenuItem langItem = new JMenuItem(name + " (" + langLocale.getLanguage().toUpperCase() + ")");
            langItem.addActionListener(e -> {
                controller.onLanguageChange(langLocale);
            });

            langs.add(langItem);
        }

        help.add(langs);

        JMenuItem documentation = new JMenuItem(Lang.get("menu.help.documentation"));
        documentation.addActionListener(e -> controller.onOpenDocDialog());
        help.add(documentation);

        JMenuItem about = new JMenuItem(Lang.get("menu.help.about"));
        about.addActionListener(e -> controller.onOpenAboutDialog());
        help.add(about);

        add(fileMenu);
        add(view);
        add(help);
    }
}