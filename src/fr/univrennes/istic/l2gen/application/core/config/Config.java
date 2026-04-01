package fr.univrennes.istic.l2gen.application.core.config;

import java.util.prefs.Preferences;

import fr.univrennes.istic.l2gen.application.VectorReport;

public final class Config {
    private static Preferences prefs = Preferences.userNodeForPackage(VectorReport.class);

    public static Preferences get() {
        return prefs;
    }
}
