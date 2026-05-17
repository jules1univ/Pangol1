package fr.univrennes.istic.l2gen.application.gui.dialog.dev;

import java.util.Map;

@FunctionalInterface
public interface CommandExecutor {

    String execute(Map<String, Object> values);
}