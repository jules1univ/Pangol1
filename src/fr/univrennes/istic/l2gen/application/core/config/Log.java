package fr.univrennes.istic.l2gen.application.core.config;

import java.lang.management.ManagementFactory;

public final class Log {
    public static boolean DEBUG_MODE = ManagementFactory.getRuntimeMXBean().getInputArguments().toString()
            .indexOf("-agentlib:jdwp") > 0;

    public static void debug(Runnable r) {
        if (DEBUG_MODE) {
            r.run();
        }
    }

    public static void mode(Runnable dbg, Runnable prod) {
        if (DEBUG_MODE) {
            dbg.run();
        } else {
            prod.run();
        }
    }

    public static void debug(String message) {
        if (DEBUG_MODE) {
            System.out.println(message);
        }
    }

    public static void debug(String message, Exception e) {
        if (DEBUG_MODE) {
            System.out.println(message);
            e.printStackTrace();
        }
    }

}
