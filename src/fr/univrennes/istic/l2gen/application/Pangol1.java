package fr.univrennes.istic.l2gen.application;

import fr.univrennes.istic.l2gen.application.core.CoreApp;
import fr.univrennes.istic.l2gen.application.gui.GUIApp;

public class Pangol1 {

    public static void main(String[] args) throws Exception {
        CoreApp<?> app = new GUIApp();
        app.start();
    }
}
