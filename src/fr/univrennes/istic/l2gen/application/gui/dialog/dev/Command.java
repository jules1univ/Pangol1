package fr.univrennes.istic.l2gen.application.gui.dialog.dev;

import java.util.ArrayList;
import java.util.List;

public class Command {

    private final String id;
    private final String label;
    private final CommandType type;

    private final List<Command> children = new ArrayList<>();

    private CommandExecutor executor;

    public Command(String id, String label, CommandType type) {
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public Command(String id, String label) {
        this.id = id;
        this.label = label;
        this.type = CommandType.NONE;
    }

    public Command child(Command child) {
        children.add(child);
        return this;
    }

    public Command executor(CommandExecutor executor) {
        this.executor = executor;
        return this;
    }

    public String id() {
        return id;
    }

    public String label() {
        return label;
    }

    public CommandType type() {
        return type;
    }

    public List<Command> children() {
        return children;
    }

    public CommandExecutor executor() {
        return executor;
    }

    @Override
    public String toString() {
        return label;
    }
}