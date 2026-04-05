package fr.univrennes.istic.l2gen.application.core.config;

import java.util.prefs.Preferences;

import fr.univrennes.istic.l2gen.application.Pangolin;

public final class Config {
    private static Preferences prefs = Preferences.userNodeForPackage(Pangolin.class);

    public static Preferences get() {
        return prefs;
    }
}
