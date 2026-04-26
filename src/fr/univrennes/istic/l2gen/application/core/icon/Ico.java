package fr.univrennes.istic.l2gen.application.core.icon;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import fr.univrennes.istic.l2gen.application.core.config.Config;

public final class Ico {
    public static FlatSVGIcon get(String path) {
        String newPath = path.replace(".svg", Config.DARK_MODE ? "_light.svg" : "_dark.svg");
        return new FlatSVGIcon(newPath, 14, 14);
    }
}
