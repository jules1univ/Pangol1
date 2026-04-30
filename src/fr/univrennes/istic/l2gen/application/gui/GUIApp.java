package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreApp;
import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Log;
import fr.univrennes.istic.l2gen.application.core.lang.Lang;
import fr.univrennes.istic.l2gen.application.core.services.TableService;
import fr.univrennes.istic.l2gen.application.gui.main.MainView;
import fr.univrennes.istic.l2gen.application.gui.main.SplashScreen;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Locale;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public final class GUIApp extends CoreApp<GUIController> {

    public GUIApp() {
        super(GUIController.getInstance());
    }

    public GUIApp(GUIController controller) {
        super(controller);
    }

    @Override
    public void start() {
        FlatLaf.setGlobalExtraDefaults(Collections.singletonMap("@accentColor", "#39B763"));

        boolean useFlatLaf = Config.get().getBoolean("settings.appearance.use_flatlaf", true);
        if (useFlatLaf && !FlatLightLaf.setup()) {
            JOptionPane.showMessageDialog(null,
                    Lang.get("error.initfl_message"),
                    Lang.get("error.initfl_title"),
                    JOptionPane.ERROR_MESSAGE);
        }

        String langTag = Config.get().get("settings.general.language", Lang.getDefaultLocale().toLanguageTag());
        Locale locale = Locale.forLanguageTag(langTag);
        if (Lang.isSupported(locale)) {
            Lang.setLocale(locale);
        }

        if (useFlatLaf) {
            int hour = LocalTime.now().getHour();
            int minHour = Config.get().getInt("settings.appearance.auto_start", 18);
            int maxHour = Config.get().getInt("settings.appearance.auto_end", 6);

            minHour = Math.min(minHour, maxHour);
            maxHour = Math.max(minHour, maxHour);

            int theme = Config.get().getInt("settings.appearance.theme", 3);

            try {
                switch (theme) {
                    case 0 -> {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                        Config.DARK_MODE = false;
                    }
                    case 1 -> {
                        UIManager.setLookAndFeel(new FlatDarkLaf());
                        Config.DARK_MODE = true;
                    }
                    case 2 -> {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        Config.DARK_MODE = UIManager.getLookAndFeel() instanceof FlatDarkLaf;
                    }
                    default -> {
                        if (hour >= maxHour && hour < minHour) {
                            UIManager.setLookAndFeel(new FlatDarkLaf());
                            Config.DARK_MODE = true;
                        } else {
                            UIManager.setLookAndFeel(new FlatLightLaf());
                            Config.DARK_MODE = false;
                        }
                    }
                }
            } catch (Exception e) {
                Log.debug("Failed to set Flatlaf theme", e);
            }
        }

        SwingUtilities.invokeLater(() -> {
            SplashScreen splash = new SplashScreen();

            new Thread(() -> {
                splash.setStatus(Lang.get("app.loading.recents"));
                TableService.loadRecents();

                splash.setStatus(Lang.get("app.loading.ui"));
                MainView mainView = new MainView(splash);
                this.controller.setMainView(mainView);
                this.controller.onStart();
            }).start();
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.controller.onStop();
            TableService.saveRecents();
        }));
    }
}