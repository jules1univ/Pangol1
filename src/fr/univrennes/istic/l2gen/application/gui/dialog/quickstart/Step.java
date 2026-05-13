package fr.univrennes.istic.l2gen.application.gui.dialog.quickstart;

import java.awt.Component;
import java.util.function.Supplier;

public record Step(String title, String body, Runnable onEnter, Runnable onClose, Supplier<Component> targetSupplier) {

    public Step(String title, String body, Runnable onEnter, Supplier<Component> targetSupplier) {
        this(title, body, onEnter, null, targetSupplier);
    }
}