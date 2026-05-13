package fr.univrennes.istic.l2gen.application.core.notebook;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests pour NoteBookText
 */
public class NoteBookTextTest {

    /**
     * Verifie que NoteBookText ne peut etre instancie qu'avec du texte
     */
    @Test
    public void testNoteBookTextCreationWithTextOnly() {
        String text = "Test Text";
        NoteBookText noteBookText = new NoteBookText(text);

        assertNotNull("NoteBookText should be created", noteBookText);
        assertEquals("Text should be stored correctly", text, noteBookText.getText());
    }

    /**
     * Verifie que NoteBookText stock un null RTF quand on ne donne rien
     */
    @Test
    public void testNoteBookTextRtfIsNullWhenNotProvided() {
        String text = "Test text";
        NoteBookText noteBookText = new NoteBookText(text);

        assertNull("RTF should be null when not provided", noteBookText.getRtf());
    }

    /**
     * Verifie que NoteBookText peut etre instancie avec du texte et un RTF
     */
    @Test
    public void testNoteBookTextCreationWithTextAndRtf() {
        String text = "Formatted text";
        String rtf = "{\\rtf1 This is content in RTF}";
        NoteBookText noteBookText = new NoteBookText(text, rtf);

        assertNotNull("NoteBookText should be created", noteBookText);
        assertEquals("Text should be stored correctly", text, noteBookText.getText());
        assertEquals("RTF should be stored correctly", rtf, noteBookText.getRtf());
    }

    /**
     * Verifie que getText() renvoie le texte correctement
     */
    @Test
    public void testGetTextReturnsCorrectValue() {
        String text = "Sample text content";
        NoteBookText noteBookText = new NoteBookText(text);

        assertEquals("getText() should return the text", text, noteBookText.getText());
    }

    /**
     * Verifie que getRtf() renvoie le RTF correctement
     */
    @Test
    public void testGetRtfReturnsCorrectValue() {
        String text = "Text";
        String rtf = "{\\rtf1 test}";
        NoteBookText noteBookText = new NoteBookText(text, rtf);

        assertEquals("getRtf() should return the RTF", rtf, noteBookText.getRtf());
    }

    /**
     * Verifie que exportHTML() met le texte dabns une balise
     * <p>
     */
    @Test
    public void testExportHTMLWrapsInParagraph() {
        String text = "Hello World";
        NoteBookText noteBookText = new NoteBookText(text);
        StringBuilder html = new StringBuilder();

        noteBookText.exportHTML(html);

        String result = html.toString();
        assertTrue("HTML should contain opening <p> tag", result.contains("<p>"));
        assertTrue("HTML should contain closing </p> tag", result.contains("</p>"));
        assertTrue("HTML should contain the text", result.contains(text));
    }

    /**
     * Verifie que exportHTML() creer une structure HTML valide
     */
    @Test
    public void testExportHTMLStructure() {
        String text = "Test Content";
        NoteBookText noteBookText = new NoteBookText(text);
        StringBuilder html = new StringBuilder();

        noteBookText.exportHTML(html);

        String result = html.toString();
        assertEquals("HTML should be <p>Test Content</p>",
                "<p>" + text + "</p>", result);
    }

    /**
     * Verifie que exportHTML() marche avec des caracteres speciaux
     */
    @Test
    public void testExportHTMLWithSpecialCharacters() {
        String text = "Text with <special> & \"characters\"";
        NoteBookText noteBookText = new NoteBookText(text);
        StringBuilder html = new StringBuilder();

        noteBookText.exportHTML(html);

        String result = html.toString();
        assertTrue("HTML should contain the special characters", result.contains(text));
    }

    /**
     * Verifie que NoteBookText implemente l'interface NoteBookValue
     */
    @Test
    public void testNoteBookTextImplementsNoteBookValue() {
        NoteBookText noteBookText = new NoteBookText("Test");
        assertTrue("NoteBookText should implement NoteBookValue",
                noteBookText instanceof NoteBookValue);
    }

    /**
     * Verifie que le texte vide est gere correctement
     */
    @Test
    public void testNoteBookTextWithEmptyText() {
        String text = "";
        NoteBookText noteBookText = new NoteBookText(text);

        assertEquals("Empty text should be stored", text, noteBookText.getText());

        StringBuilder html = new StringBuilder();
        noteBookText.exportHTML(html);
        assertEquals("HTML with empty text should be <p></p>", "<p></p>", html.toString());
    }
}
