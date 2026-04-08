package fr.univrennes.istic.l2gen.application.core.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FileServiceTest {

    @Test
    public void testGetExtension() {
        Assert.assertEquals("csv", FileService.getExtension(new java.io.File("sample.csv")));
        Assert.assertEquals("parquet", FileService.getExtension(new java.io.File("sample.PARQUET")));
        Assert.assertEquals("", FileService.getExtension(new java.io.File("sample")));
        Assert.assertEquals("", FileService.getExtension(new java.io.File(".hidden")));
    }

    @Test
    public void testGetRemoteFileNameFromContentDisposition() throws Exception {
        StubHttpURLConnection connection = new StubHttpURLConnection(new URL("http://example.com/data"));
        connection.setHeader("Content-Disposition", "attachment; filename*=UTF-8''r%C3%A9sultat.csv");

        Assert.assertEquals("résultat.csv",
                FileService.getRemoteFileName(connection, new URL("http://example.com/data")));
    }

    @Test
    public void testGetRemoteFileNameFallsBackToFilenameAndContentType() throws Exception {
        StubHttpURLConnection filenameConnection = new StubHttpURLConnection(new URL("http://example.com/data"));
        filenameConnection.setHeader("Content-Disposition", "attachment; filename=report.tsv");
        Assert.assertEquals("report.tsv",
                FileService.getRemoteFileName(filenameConnection, new URL("http://example.com/data")));

        StubHttpURLConnection contentTypeConnection = new StubHttpURLConnection(new URL("http://example.com/archive"));
        contentTypeConnection.setContentType("application/vnd.apache.parquet");
        Assert.assertEquals("archive.parquet",
                FileService.getRemoteFileName(contentTypeConnection, new URL("http://example.com/archive")));

        StubHttpURLConnection emptyPathConnection = new StubHttpURLConnection(new URL("http://example.com/"));
        emptyPathConnection.setContentType("text/plain");
        String generated = FileService.getRemoteFileName(emptyPathConnection, new URL("http://example.com/"));
        Assert.assertTrue(generated.startsWith("file_"));
        Assert.assertTrue(generated.endsWith(".txt"));
    }

    @Test
    public void testInvalidEncodedFilenameFallsBackToRegularFilename() throws Exception {
        StubHttpURLConnection connection = new StubHttpURLConnection(new URL("http://example.com/data"));
        connection.setHeader("Content-Disposition", "attachment; filename*=UTF-8''bad%ZZ; filename=backup.csv");

        Assert.assertEquals("backup.csv",
                FileService.getRemoteFileName(connection, new URL("http://example.com/data")));
    }

    private static class StubHttpURLConnection extends HttpURLConnection {

        private final Map<String, String> headers = new HashMap<>();
        private String contentType;

        StubHttpURLConnection(URL url) {
            super(url);
        }

        void setHeader(String name, String value) {
            headers.put(name, value);
        }

        void setContentType(String contentType) {
            this.contentType = contentType;
        }

        @Override
        public String getHeaderField(String name) {
            return headers.get(name);
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public void disconnect() {
        }

        @Override
        public boolean usingProxy() {
            return false;
        }

        @Override
        public void connect() throws IOException {
        }
    }
}