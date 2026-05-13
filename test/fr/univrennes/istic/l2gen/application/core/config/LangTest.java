package fr.univrennes.istic.l2gen.application.core.config;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.Set;

/**
 * Tests pour Lang utility class
 */
public class LangTest {

    /**
     * Verifie que Lang est une final class
     */
    @Test
    public void testLangIsFinal() {
        Class<?> langClass = Lang.class;
        assertTrue("Lang class must be final", Modifier.isFinal(langClass.getModifiers()));
    }

    /**
     * Verifie que Lang a une methode getDefaultLocale() static
     */
    @Test
    public void testLangHasGetDefaultLocale() {
        try {
            Method method = Lang.class.getMethod("getDefaultLocale");
            assertNotNull("Lang must have getDefaultLocale() method", method);
            assertTrue("getDefaultLocale() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang getDefaultLocale method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que getDefaultLocale() renvoie un type Locale
     */
    @Test
    public void testGetDefaultLocaleReturnsLocale() {
        Locale locale = Lang.getDefaultLocale();
        assertNotNull("getDefaultLocale() must return a Locale", locale);
    }

    /**
     * Verifie que Lang a une methode setLocale() static
     */
    @Test
    public void testLangHasSetLocale() {
        try {
            Method method = Lang.class.getMethod("setLocale", Locale.class);
            assertNotNull("Lang must have setLocale(Locale) method", method);
            assertTrue("setLocale() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang setLocale method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que Lang a une methode getLocale()
     */
    @Test
    public void testLangHasGetLocale() {
        try {
            Method method = Lang.class.getMethod("getLocale");
            assertNotNull("Lang must have getLocale() method", method);
            assertTrue("getLocale() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang getLocale method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que Lang a une methode get(String) pour la traduction
     */
    @Test
    public void testLangHasGetTranslation() {
        try {
            Method method = Lang.class.getMethod("get", String.class);
            assertNotNull("Lang must have get(String) method for translations", method);
            assertTrue("get() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang get method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que Lang get() renvoie un String
     */
    @Test
    public void testGetTranslationReturnsString() {
        String translation = Lang.get("app.title");
        System.out.println(translation);
        assertNotNull("get() must return a String", translation);
        assertFalse("Translation should not be empty", translation.isEmpty());
    }

    /**
     * Verifie que Lang a une methode isSupported() static
     */
    @Test
    public void testLangHasIsSupported() {
        try {
            Method method = Lang.class.getMethod("isSupported", Locale.class);
            assertNotNull("Lang must have isSupported(Locale) method", method);
            assertTrue("isSupported() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang isSupported method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que isSupported() renvoie un boolean
     */
    @Test
    public void testIsSupportedReturnsBooleanValue() {
        Locale defaultLocale = Lang.getDefaultLocale();
        boolean supported = Lang.isSupported(defaultLocale);
        assertTrue("Default locale should be supported", supported);
    }

    /**
     * Verifie que Lang a une methode getSupportedLanguages() static
     */
    @Test
    public void testLangHasGetSupportedLanguages() {
        try {
            Method method = Lang.class.getMethod("getSupportedLanguages");
            assertNotNull("Lang must have getSupportedLanguages() method", method);
            assertTrue("getSupportedLanguages() must be static", Modifier.isStatic(method.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Lang getSupportedLanguages method cannot be found: " + e.getMessage());
        }
    }

    /**
     * Verifie que getSupportedLanguages() renvoie un Set
     */
    @Test
    public void testGetSupportedLanguagesReturnsSet() {
        Set<String> languages = Lang.getSupportedLanguages();
        assertNotNull("getSupportedLanguages() must return a Set", languages);
        assertFalse("Supported languages set should not be empty", languages.isEmpty());
    }

    /**
     * Verifie que setLocale() et getLocale() sont ensembles
     */
    @Test
    public void testSetAndGetLocaleConsistency() {
        Locale original = Lang.getLocale();
        assertNotNull("Original locale should not be null", original);

        Locale testLocale = Locale.ENGLISH;
        Lang.setLocale(testLocale);
        Locale afterSet = Lang.getLocale();
        assertEquals("After setLocale, getLocale should return same locale", testLocale, afterSet);

        Lang.setLocale(original);
    }
}
