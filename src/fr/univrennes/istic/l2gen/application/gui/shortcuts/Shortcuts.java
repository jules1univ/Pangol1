package fr.univrennes.istic.l2gen.application.gui.shortcuts;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

import javax.swing.KeyStroke;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.config.Log;

public final class Shortcuts {
    public static final String KEY_TABLE_OPEN = "settings.shortcuts.table.open";
    public static final String KEY_TABLE_CLOSE = "settings.shortcuts.table.close";
    public static final String KEY_VIEW_FILTER = "settings.shortcuts.view.filter";
    public static final String KEY_VIEW_SETTINGS = "settings.shortcuts.view.settings";
    public static final String KEY_SORT_ASC = "settings.shortcuts.sort.sort_asc";
    public static final String KEY_SORT_DESC = "settings.shortcuts.sort.sort_desc";
    public static final String KEY_NOTEBOOK_UNDO = "settings.shortcuts.notebook.undo";
    public static final String KEY_NOTEBOOK_REDO = "settings.shortcuts.notebook.redo";
    public static final String KEY_FILE_OPEN_URL = "settings.shortcuts.file.open_url";
    public static final String KEY_FILE_EXIT = "settings.shortcuts.file.exit";
    public static final String KEY_HELP_DOCUMENTATION = "settings.shortcuts.help.documentation";

    public static final String DEFAULT_TABLE_OPEN = "Ctrl+O";
    public static final String DEFAULT_TABLE_CLOSE = "Ctrl+W";
    public static final String DEFAULT_VIEW_FILTER = "Ctrl+F";
    public static final String DEFAULT_VIEW_SETTINGS = "Ctrl+,";
    public static final String DEFAULT_SORT_ASC = "Ctrl+Shift+A";
    public static final String DEFAULT_SORT_DESC = "Ctrl+Shift+D";
    public static final String DEFAULT_NOTEBOOK_UNDO = "Ctrl+Z";
    public static final String DEFAULT_NOTEBOOK_REDO = "Ctrl+Y";
    public static final String DEFAULT_FILE_OPEN_URL = "Ctrl+Shift+O";
    public static final String DEFAULT_FILE_EXIT = "Ctrl+Q";
    public static final String DEFAULT_HELP_DOCUMENTATION = "F1";

    private Shortcuts() {
    }

    public static String getShortcut(String key, String defaultValue) {
        return Config.getString(key, defaultValue);
    }

    public static KeyStroke getKeyStroke(String key, String defaultValue) {
        String value = getShortcut(key, defaultValue);
        KeyStroke keyStroke = parseKeyStroke(value);
        if (keyStroke == null && value != null && !value.isBlank()) {
            Log.debug("Invalid shortcut value for " + key + ": " + value);
        }
        return keyStroke;
    }

    public static String formatKeyStroke(KeyStroke keyStroke) {
        if (keyStroke == null) {
            return "";
        }
        return formatKeyStroke(keyStroke.getKeyCode(), keyStroke.getModifiers());
    }

    public static boolean isValidShortcut(String value) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return parseKeyStroke(value) != null;
    }

    public static KeyStroke parseKeyStroke(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        String[] parts = trimmed.split("\\+");
        int modifiers = 0;
        String keyToken = null;

        for (String part : parts) {
            if (part == null) {
                continue;
            }

            String token = part.trim();
            if (token.isEmpty()) {
                continue;
            }

            String upper = token.toUpperCase(Locale.ROOT);
            switch (upper) {
                case "CTRL":
                case "CONTROL":
                    modifiers |= InputEvent.CTRL_DOWN_MASK;
                    continue;
                case "SHIFT":
                    modifiers |= InputEvent.SHIFT_DOWN_MASK;
                    continue;
                case "ALT":
                case "OPTION":
                    modifiers |= InputEvent.ALT_DOWN_MASK;
                    continue;
                case "CMD":
                case "COMMAND":
                case "META":
                    modifiers |= InputEvent.META_DOWN_MASK;
                    continue;
                default:
                    keyToken = token;
                    break;
            }
        }

        if (keyToken == null) {
            return null;
        }

        Integer keyCode = resolveKeyCode(keyToken);
        if (keyCode == null) {
            return null;
        }

        return KeyStroke.getKeyStroke(keyCode, modifiers);
    }

    private static String formatKeyStroke(int keyCode, int modifiers) {
        String keyToken = resolveKeyToken(keyCode);
        if (keyToken == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        if ((modifiers & InputEvent.CTRL_DOWN_MASK) != 0) {
            result.append("Ctrl+");
        }
        if ((modifiers & InputEvent.SHIFT_DOWN_MASK) != 0) {
            result.append("Shift+");
        }
        if ((modifiers & InputEvent.ALT_DOWN_MASK) != 0) {
            result.append("Alt+");
        }
        if ((modifiers & InputEvent.META_DOWN_MASK) != 0) {
            result.append("Cmd+");
        }

        result.append(keyToken);
        return result.toString();
    }

    private static Integer resolveKeyCode(String token) {
        String upper = token.trim().toUpperCase(Locale.ROOT);
        if (upper.isEmpty()) {
            return null;
        }

        if (upper.startsWith("F") && upper.length() <= 3) {
            try {
                int value = Integer.parseInt(upper.substring(1));
                if (value >= 1 && value <= 12) {
                    return KeyEvent.VK_F1 + (value - 1);
                }
            } catch (NumberFormatException ignored) {
            }
        }

        if (upper.length() == 1) {
            char ch = upper.charAt(0);
            if (Character.isLetterOrDigit(ch)) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(ch);
                return keyCode == KeyEvent.VK_UNDEFINED ? null : keyCode;
            }
        }

        switch (upper) {
            case ",":
            case "COMMA":
                return KeyEvent.VK_COMMA;
            case ".":
            case "PERIOD":
            case "DOT":
                return KeyEvent.VK_PERIOD;
            case "/":
            case "SLASH":
                return KeyEvent.VK_SLASH;
            case "-":
            case "MINUS":
            case "DASH":
                return KeyEvent.VK_MINUS;
            case "=":
            case "EQUALS":
                return KeyEvent.VK_EQUALS;
            case ";":
            case "SEMICOLON":
                return KeyEvent.VK_SEMICOLON;
            case "'":
            case "QUOTE":
                return KeyEvent.VK_QUOTE;
            case "[":
            case "OPEN_BRACKET":
            case "LBRACKET":
                return KeyEvent.VK_OPEN_BRACKET;
            case "]":
            case "CLOSE_BRACKET":
            case "RBRACKET":
                return KeyEvent.VK_CLOSE_BRACKET;
            case "\\":
            case "BACKSLASH":
            case "BACK_SLASH":
                return KeyEvent.VK_BACK_SLASH;
            case "SPACE":
                return KeyEvent.VK_SPACE;
            case "TAB":
                return KeyEvent.VK_TAB;
            case "ENTER":
            case "RETURN":
                return KeyEvent.VK_ENTER;
            case "ESC":
            case "ESCAPE":
                return KeyEvent.VK_ESCAPE;
            case "BACKSPACE":
                return KeyEvent.VK_BACK_SPACE;
            case "DELETE":
            case "DEL":
                return KeyEvent.VK_DELETE;
            case "UP":
                return KeyEvent.VK_UP;
            case "DOWN":
                return KeyEvent.VK_DOWN;
            case "LEFT":
                return KeyEvent.VK_LEFT;
            case "RIGHT":
                return KeyEvent.VK_RIGHT;
            default:
                return null;
        }
    }

    private static String resolveKeyToken(int keyCode) {
        if (keyCode >= KeyEvent.VK_F1 && keyCode <= KeyEvent.VK_F12) {
            return "F" + (keyCode - KeyEvent.VK_F1 + 1);
        }

        if ((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z)
                || (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9)) {
            return String.valueOf((char) keyCode);
        }

        switch (keyCode) {
            case KeyEvent.VK_COMMA:
                return ",";
            case KeyEvent.VK_PERIOD:
                return ".";
            case KeyEvent.VK_SLASH:
                return "/";
            case KeyEvent.VK_MINUS:
                return "-";
            case KeyEvent.VK_EQUALS:
                return "=";
            case KeyEvent.VK_SEMICOLON:
                return ";";
            case KeyEvent.VK_QUOTE:
                return "'";
            case KeyEvent.VK_OPEN_BRACKET:
                return "[";
            case KeyEvent.VK_CLOSE_BRACKET:
                return "]";
            case KeyEvent.VK_BACK_SLASH:
                return "\\";
            case KeyEvent.VK_SPACE:
                return "Space";
            case KeyEvent.VK_TAB:
                return "Tab";
            case KeyEvent.VK_ENTER:
                return "Enter";
            case KeyEvent.VK_ESCAPE:
                return "Esc";
            case KeyEvent.VK_BACK_SPACE:
                return "Backspace";
            case KeyEvent.VK_DELETE:
                return "Delete";
            case KeyEvent.VK_UP:
                return "Up";
            case KeyEvent.VK_DOWN:
                return "Down";
            case KeyEvent.VK_LEFT:
                return "Left";
            case KeyEvent.VK_RIGHT:
                return "Right";
            default:
                return null;
        }
    }
}
