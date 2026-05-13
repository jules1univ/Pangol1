package fr.univrennes.istic.l2gen.application.core.notebook;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;


/**
 * Tests pour NoteBookChart
 * On verifie la structure
 */
public class NoteBookChartTest {

    /**
     * Verifie que NoteBookChart est une classe final
     */
    @Test
    public void testNoteBookChartIsFinal() {
        Class<?> chartClass = NoteBookChart.class;
        assertTrue("NoteBookChart must be final", Modifier.isFinal(chartClass.getModifiers()));
    }

    /**
     * Verifie que NoteBookChart implemente l'interface NoteBookValue
     */
    @Test
    public void testNoteBookChartImplementsNoteBookValue() {
        Class<?> chartClass = NoteBookChart.class;
        boolean implementsInterface = NoteBookValue.class.isAssignableFrom(chartClass);
        assertTrue("NoteBookChart must implement NoteBookValue", implementsInterface);
    }

    /**
     * Verifie que NoteBookChart a un constructeur
     */
    @Test
    public void testNoteBookChartHasConstructor() {
        Constructor<?>[] constructors = NoteBookChart.class.getDeclaredConstructors();
        assertTrue("NoteBookChart must have at least one constructor", constructors.length > 0);
    }

    /**
     * Verifie que NoteBookChart possede une methode exportHTML
     */
    @Test
    public void testNoteBookChartHasExportHTMLMethod() {
        try {
            assertNotNull("NoteBookChart must have exportHTML method",
                    NoteBookChart.class.getMethod("exportHTML", StringBuilder.class));
        } catch (NoSuchMethodException e) {
            fail("NoteBookChart exportHTML method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que NoteBookChart possede des champs prives requis
     */
    @Test
    public void testNoteBookChartHasPrivateFields() {
        java.lang.reflect.Field[] fields = NoteBookChart.class.getDeclaredFields();
        assertTrue("NoteBookChart must have private fields", fields.length > 0);

        boolean hasPrivateFields = false;
        for (java.lang.reflect.Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                hasPrivateFields = true;
                break;
            }
        }

        assertTrue("NoteBookChart must have private fields for encapsulation", hasPrivateFields);
    }

    /**
     * Verifie que NoteBookChart n'est pas abstraite
     */
    @Test
    public void testNoteBookChartIsNotAbstract() {
        assertFalse("NoteBookChart must not be abstract", Modifier.isAbstract(NoteBookChart.class.getModifiers()));
    }

    /**
     * Verifie que NoteBookChart a plusieurs constructeurs
     */
    @Test
    public void testNoteBookChartConstructorStructure() {
        Constructor<?>[] constructors = NoteBookChart.class.getDeclaredConstructors();

        int accessibleConstructors = 0;
        for (Constructor<?> constructor : constructors) {
            if (!Modifier.isPrivate(constructor.getModifiers())) {
                accessibleConstructors++;
            }
        }

        assertTrue("NoteBookChart must have accessible constructor(s)", accessibleConstructors > 0);
    }

    /**
     * Verifie que NoteBookChart est dans le bon package
     */
    @Test
    public void testNoteBookChartPackage() {
        String packageName = NoteBookChart.class.getPackage().getName();
        assertEquals("NoteBookChart must be in notebook package", "fr.univrennes.istic.l2gen.application.core.notebook",
                packageName);
    }
}
