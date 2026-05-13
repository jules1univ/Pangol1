package fr.univrennes.istic.l2gen.application;

import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

public class Pangol1Test {

    @Test
    public void testPangol1Instantiation() {
        Pangol1 pangol1 = new Pangol1();
        assertNotNull("Pangol1 doit pouvoir être instantié", pangol1);
    }

    /**
     * Pangol1 doit avoir une class main
     * 
     * On verifie que La méthode main(String[]) existe
     */
    @Test
    public void testPangol1HasMainMethod() {
        try {
            Method mainP = Pangol1.class.getMethod("main", String[].class);
            assertNotNull("Pangol1 n'a pas de main", mainP);
        } catch (NoSuchMethodException e) {
            fail("Pangol1 doit avoir une methode main(String[]): " + e.getMessage());
        }
    }
}