package fr.univrennes.istic.l2gen.application.core.notebook;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.nio.file.Files;
import java.util.Base64;

import javax.imageio.ImageIO;

import fr.univrennes.istic.l2gen.application.core.config.Log;

public final class NoteBookImage implements NoteBookValue {

    private File image;

    public NoteBookImage(File file) {
        this.image = file;
    }

    @Override
    public void exportHTML(StringBuilder html) {
        // encode to base64
        try {
            String base64 = ImageIO.write(ImageIO.read(image), "png", new ByteArrayOutputStream())
                    ? Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()))
                    : "";
            html.append("<img src=\"data:image/png;base64,").append(base64).append("\" alt=\"Image\" />");
        } catch (Exception e) {
            Log.debug("Failed to export image to HTML", e);
        }
    }

    @Override
    public void save(DataOutputStream out) {
        try {
            out.writeUTF("IMAGE");
            out.writeUTF(image.getAbsolutePath());
        } catch (Exception e) {
            Log.debug("Failed to save image", e);
        }
    }

    @Override
    public void load(DataInputStream in) {
        try {
            this.image = new File(in.readUTF());
        } catch (Exception e) {
            Log.debug("Failed to load image", e);
        }
    }

}
