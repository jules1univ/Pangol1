package fr.univrennes.istic.l2gen.application.core.services;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests unitaires pour ObjectService
 * Verifient save/load sur des fichiers temporaires
 */
public class ObjectServiceTest {

    @Test
    public void testSaveAndLoadString() throws Exception {
        Path tmp = Files.createTempDirectory("objsvc");
        File f = tmp.resolve("str.ser").toFile();

        ObjectService.save(f, "hello-world");

        Optional<String> loaded = ObjectService.load(f, String.class);
        assertTrue(loaded.isPresent());
        assertEquals("hello-world", loaded.get());

        // cleanup
        f.delete();
        tmp.toFile().delete();
    }

    @Test
    public void testTypeMismatchReturnsEmpty() throws Exception {
        Path tmp = Files.createTempDirectory("objsvc");
        File f = tmp.resolve("num.ser").toFile();

        ObjectService.save(f, Integer.valueOf(42));
        Optional<String> loaded = ObjectService.load(f, String.class);
        assertFalse(loaded.isPresent());

        f.delete();
        tmp.toFile().delete();
    }
}
