package fr.univrennes.istic.l2gen.application.gui.dialog.settings;

import fr.univrennes.istic.l2gen.application.core.config.Config;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.JPanel;
import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.prefs.BackingStoreException;

public abstract class SettingsTestSupport {

    public static void clearConfig() throws BackingStoreException {
        Config.get().clear();
    }

    public static <T> T getField(Object target, String fieldName, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return type.cast(field.get(target));
    }

    public static Object getFieldValue(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    public static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    public static Object invoke(Object target, String methodName, Class<?>[] parameterTypes, Object... args)
            throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }

    public static <T> T allocate(Class<T> type) throws Exception {
        return type.cast(unsafe().allocateInstance(type));
    }

    public static <T extends Component> T findComponent(Component root, Class<T> type) {
        if (type.isInstance(root)) {
            return type.cast(root);
        }
        if (root instanceof JComponent component) {
            for (Component child : component.getComponents()) {
                T found = findComponent(child, type);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    public static JPanel getViewportViewPanel(JScrollPane scrollPane) {
        JViewport viewport = scrollPane.getViewport();
        return (JPanel) viewport.getView();
    }

    private static sun.misc.Unsafe unsafe() throws Exception {
        Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        return (sun.misc.Unsafe) field.get(null);
    }
}