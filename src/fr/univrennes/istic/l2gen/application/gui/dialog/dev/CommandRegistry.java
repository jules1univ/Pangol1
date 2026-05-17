package fr.univrennes.istic.l2gen.application.gui.dialog.dev;

import java.util.List;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Lang;

public class CommandRegistry {

        private static final List<Command> COMMANDS = List.of(

                        new Command("settings", Lang.get("devconsole.commands.settings"), CommandType.ROOT)

                                        .child(
                                                        new Command("options", Lang
                                                                        .get("devconsole.commands.settings.options"),
                                                                        CommandType.SELECT)
                                                                        .child(new Command("list", Lang.get(
                                                                                        "devconsole.commands.settings.options.list")))
                                                                        .child(new Command("set", Lang.get(
                                                                                        "devconsole.commands.settings.options.set"),
                                                                                        CommandType.INPUT)))

                                        .executor(values -> {
                                                if (values.get("options").equals("list")) {
                                                        StringBuilder sb = new StringBuilder();
                                                        sb.append("Current settings:\n");
                                                        for (String key : Config.getSettingKeys()) {
                                                                sb.append("- " + key).append("\n");
                                                        }
                                                        return sb.toString();
                                                } else if (values.get("options").equals("set")) {
                                                }

                                                return "";
                                        }));

        public static List<Command> getCommands() {
                return COMMANDS;
        }
}