package fr.univrennes.istic.l2gen.application.core.services;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.univrennes.istic.l2gen.application.core.config.Config;
import fr.univrennes.istic.l2gen.application.core.table.DataTable;

public class TableServiceTest {

    private final List<File> tempPaths = new ArrayList<>();
    private HttpServer server;
    private File httpTargetDir;

    @Before
    public void resetState() throws Exception {
        clearLoadedTables();
        TableService.getRecentTables().clear();
        Config.get().remove("recents");
        startServer();
    }

    @After
    public void cleanup() throws Exception {
        if (server != null) {
            server.stop(0);
        }
        clearLoadedTables();
        TableService.getRecentTables().clear();
        Config.get().remove("recents");
        for (File path : tempPaths) {
            deleteRecursively(path);
        }
        if (httpTargetDir != null) {
            deleteRecursively(httpTargetDir);
        }
    }

    @Test
    public void testLoadParquetCsvDirectoryAndCache() throws Exception {
        File directory = Files.createTempDirectory("tableservice-dir").toFile();
        tempPaths.add(directory);

        File parquet = new File(directory, "sample.parquet");
        writeParquet(parquet,
                "SELECT * FROM (VALUES (1, 1.5, 'alpha'), (2, 2.5, 'beta')) AS t(id, val, txt)");

        File csv = new File(directory, "sample.csv");
        Files.writeString(csv.toPath(), "id,val,txt\n1,1.5,alpha\n2,2.5,beta\n", StandardCharsets.UTF_8);

        List<DataTable> fromDirectory = TableService.load(directory, directory);
        Assert.assertTrue(fromDirectory.size() >= 2);

        List<DataTable> fromParquet = TableService.load(parquet, directory);
        Assert.assertEquals(1, fromParquet.size());
        Assert.assertSame(fromParquet.get(0), TableService.get(parquet));

        List<DataTable> fromCsv = TableService.load(csv, directory);
        Assert.assertEquals(1, fromCsv.size());
        Assert.assertEquals(2L, fromCsv.get(0).getRowCount());
        Assert.assertEquals(3L, fromCsv.get(0).getColumnCount());

        DataTable cached = TableService.get(csv);
        Assert.assertSame(fromCsv.get(0), cached);

        Set<File> recents = TableService.getRecentTables();
        TableService.addRecent(csv);
        Assert.assertTrue(recents.contains(csv));
        TableService.removeRecent(csv);
        Assert.assertFalse(recents.contains(csv));
    }

    @Test
    public void testLoadZipAndUriAndRecentsPersistence() throws Exception {
        File directory = Files.createTempDirectory("tableservice-zip").toFile();
        tempPaths.add(directory);

        File zipFile = new File(directory, "bundle.zip");
        File nestedCsv = new File(directory, "nested.csv");
        Files.writeString(nestedCsv.toPath(), "id,val\n1,3.5\n2,4.5\n", StandardCharsets.UTF_8);
        createZip(zipFile, "nested.csv", Files.readAllBytes(nestedCsv.toPath()));

        List<DataTable> fromZip = TableService.load(zipFile, directory);
        Assert.assertEquals(1, fromZip.size());

        File downloadedDir = Files.createTempDirectory("tableservice-http").toFile();
        tempPaths.add(downloadedDir);
        httpTargetDir = downloadedDir;

        List<DataTable> fromUri = TableService
                .load(URI.create("http://localhost:" + server.getAddress().getPort() + "/redirect"), downloadedDir);
        Assert.assertEquals(1, fromUri.size());
        Assert.assertEquals(2L, fromUri.get(0).getRowCount());
        Assert.assertTrue(new File(downloadedDir, "remote.csv").exists());

        TableService.addRecent(new File("/tmp/recent-one.parquet"));
        TableService.saveRecents();
        TableService.getRecentTables().clear();
        TableService.loadRecents();
        Assert.assertFalse(TableService.getRecentTables().isEmpty());
    }

    private void startServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/redirect", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                try {
                    exchange.getResponseHeaders().add("Location",
                            "http://localhost:" + server.getAddress().getPort() + "/data.csv");
                    exchange.sendResponseHeaders(302, -1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    exchange.close();
                }
            }
        });
        server.createContext("/data.csv", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) {
                try {
                    byte[] body = "id,val\n1,10.0\n2,20.0\n".getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().add("Content-Disposition", "attachment; filename=remote.csv");
                    exchange.getResponseHeaders().add("Content-Type", "text/csv");
                    exchange.sendResponseHeaders(200, body.length);
                    try (java.io.OutputStream output = exchange.getResponseBody()) {
                        output.write(body);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    exchange.close();
                }
            }
        });
        server.start();
    }

    private void createZip(File zipFile, String entryName, byte[] body) throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            zos.putNextEntry(new ZipEntry(entryName));
            zos.write(body);
            zos.closeEntry();
        }
    }

    private void writeParquet(File parquet, String selectQuery) throws Exception {
        String path = parquet.getAbsolutePath().replace("\\", "/").replace("'", "''");
        try (Connection connection = DriverManager.getConnection("jdbc:duckdb:");
                Statement statement = connection.createStatement()) {
            statement.execute("COPY (" + selectQuery + ") TO '" + path + "' (FORMAT PARQUET)");
        }
    }

    @SuppressWarnings("unchecked")
    private void clearLoadedTables() throws Exception {
        java.lang.reflect.Field field = TableService.class.getDeclaredField("loaded");
        field.setAccessible(true);
        ((java.util.Map<File, DataTable>) field.get(null)).clear();
    }

    private void deleteRecursively(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }
}