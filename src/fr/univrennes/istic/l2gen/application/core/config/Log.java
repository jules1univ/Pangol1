package fr.univrennes.istic.l2gen.application.core.config;

public final class Log {

    public static void debug(Runnable r) {
        if (Config.DEBUG_MODE) {
            r.run();
        }
    }

    public static void mode(Runnable dbg, Runnable prod) {
        if (Config.DEBUG_MODE) {
            dbg.run();
        } else {
            prod.run();
        }
    }

    public static void debug(String message) {
        if (Config.DEBUG_MODE || Config.getBoolean("settings.advanced.debug_log", false)) {
            System.out.println(message);
        }
    }

    public static void debug(String message, Exception e) {
        if (Config.DEBUG_MODE || Config.getBoolean("settings.advanced.debug_log", false)) {
            System.out.println(message);
            e.printStackTrace();
        }
    }

}
