package fr.univrennes.istic.l2gen.application.gui;

import fr.univrennes.istic.l2gen.application.core.CoreApp;
import fr.univrennes.istic.l2gen.application.core.config.Config;
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

        if (!FlatLightLaf.setup()) {
            JOptionPane.showMessageDialog(null,
                    Lang.get("error.initfl_message"),
                    Lang.get("error.initfl_title"),
                    JOptionPane.ERROR_MESSAGE);
        }

        String langTag = Config.get().get("language", Lang.getDefaultLocale().toLanguageTag());
        Locale locale = Locale.forLanguageTag(langTag);
        if (Lang.isSupported(locale)) {
            Lang.setLocale(locale);
        }

        int hour = LocalTime.now().getHour();
        if (hour >= 18 || hour < 6) {
            try {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            } catch (Exception e) {
                return;
            }
        }

        SwingUtilities.invokeLater(() -> {
            TableService.loadRecents();
            this.controller.setMainView(new MainView());
            this.controller.onStart();
        });

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.controller.onStop();
            TableService.saveRecents();
        }));
    }
}