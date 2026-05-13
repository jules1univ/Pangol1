package fr.univrennes.istic.l2gen.application.core.notebook;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;

/**
 * Tests pour NoteBookImage
 */
public class NoteBookImageTest {

    private File testImageFile;
    private File tempDir;

    @Before
    public void setUp() throws IOException {
        // On creer un dossier pour stocker l'image
        tempDir = new File(System.getProperty("java.io.tmpdir"), "noteBookImageTest");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        // On cree une image
        testImageFile = new File(tempDir, "test_image.png");
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                bufferedImage.setRGB(i, j, 0xFF0000);
            }
        }
        ImageIO.write(bufferedImage, "png", testImageFile);
    }

    @After
    public void clear() {
        if (testImageFile != null && testImageFile.exists()) {
            testImageFile.delete();
        }
        if (tempDir != null && tempDir.exists()) {
            tempDir.delete();
        }
    }

    /**
     * Verifie que NoteBookImage peut etre instancie avec un File
     */
    @Test
    public void testNoteBookImageCreation() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        assertNotNull("NoteBookImage should be created", noteBookImage);
    }

    /**
     * Verifie que getPath() renvoie le bon fichier
     */
    @Test
    public void testGetPathReturnsCorrectFile() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        File returnedFile = noteBookImage.getPath();

        assertNotNull("getPath() should not return null", returnedFile);
        assertEquals("getPath() should return the same file",
                testImageFile.getAbsolutePath(), returnedFile.getAbsolutePath());
    }

    /**
     * Verifie que getImage() renvoie une Image
     */
    @Test
    public void testGetImageReturnsImageForValidFile() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        Image image = noteBookImage.getImage();

        // L'image ne doit pas etre null si elle est bien renvoye
        assertNotNull("getImage() should return an Image for valid file", image);
    }

    /**
     * Verifie que NoteBookImage implemente l'interface NoteBookValue
     */
    @Test
    public void testNoteBookImageImplementsNoteBookValue() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        assertTrue("NoteBookImage should implement NoteBookValue",
                noteBookImage instanceof NoteBookValue);
    }

    /**
     * Verifie que exportHTML() produis un html
     */
    @Test
    public void testExportHTMLProducesValidHTML() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        StringBuilder html = new StringBuilder();

        noteBookImage.exportHTML(html);

        String result = html.toString();
        assertFalse("HTML should not be empty", result.isEmpty());
    }

    /**
     * Verifie que exportHTML() contient la balise img
     */
    @Test
    public void testExportHTMLContainsImgTag() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        StringBuilder html = new StringBuilder();

        noteBookImage.exportHTML(html);

        String result = html.toString();
        assertTrue("HTML should contain img tag", result.contains("<img"));
        assertTrue("HTML should contain base64 data", result.contains("data:image"));
    }

    /**
     * Verifie que NoteBookImage gere bien les fichiers vides
     */
    @Test
    public void testNoteBookImageHandlesNonExistentFile() {
        File nonExistentFile = new File(tempDir, "non_existent_image.png");
        NoteBookImage noteBookImage = new NoteBookImage(nonExistentFile);

        assertNotNull("NoteBookImage should be created even for non-existent file", noteBookImage);
        assertEquals("getPath() should return the file path", nonExistentFile.getAbsolutePath(),
                noteBookImage.getPath().getAbsolutePath());
    }

    /**
     * Verifie que l'on peut creer plusieurs instance de NoteBookImage
     */
    @Test
    public void testMultipleNoteBookImageInstances() {
        NoteBookImage image1 = new NoteBookImage(testImageFile);
        NoteBookImage image2 = new NoteBookImage(testImageFile);

        assertNotNull("First image should be created", image1);
        assertNotNull("Second image should be created", image2);
        assertEquals("Both should reference same file", image1.getPath(), image2.getPath());
    }

    /**
     * Verifie que exportHTML() ajoute bien au StringBuilder deja existant
     */
    @Test
    public void testExportHTMLAppendsToStringBuilder() {
        String prefix = "PREFIX:";
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);
        StringBuilder html = new StringBuilder(prefix);

        noteBookImage.exportHTML(html);

        String result = html.toString();
        assertTrue("Result should start with prefix", result.startsWith(prefix));
    }

    /**
     * Verifie que getImage() peut etre appeler plusieurs fois
     */
    @Test
    public void testGetImageCanBeCalledMultipleTimes() {
        NoteBookImage noteBookImage = new NoteBookImage(testImageFile);

        Image image1 = noteBookImage.getImage();
        Image image2 = noteBookImage.getImage();

        assertNotNull("First getImage() call should work", image1);
        assertNotNull("Second getImage() call should work", image2);
    }
}
