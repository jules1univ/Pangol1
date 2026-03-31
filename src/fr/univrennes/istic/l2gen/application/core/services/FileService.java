package fr.univrennes.istic.l2gen.application.core.services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import fr.univrennes.istic.l2gen.application.VectorReport;

public final class FileService {

    public static File getAppDataDirectory() {
        String userHome = System.getProperty("user.home");
        File appDataDir = new File(userHome, ".VectorReport");
        if (!appDataDir.exists()) {
            try {
                appDataDir.mkdirs();
            } catch (Exception e) {
                if (VectorReport.DEBUG_MODE) {
                    e.printStackTrace();
                }
            }
        }
        return appDataDir;
    }

    public static File getDownloadDirectory() {
        String userHome = System.getProperty("user.home");
        File downloadDir = new File(userHome, "Downloads");
        if (!downloadDir.exists()) {
            try {
                downloadDir.mkdirs();
            } catch (Exception e) {
                if (VectorReport.DEBUG_MODE) {
                    e.printStackTrace();
                }
            }
        }
        return downloadDir;
    }

    public static File getAppDataFile(String fileName) {
        return new File(getAppDataDirectory(), fileName);
    }

    public static String getExtension(File file) {
        String name = file.getName().toLowerCase();
        int i = name.lastIndexOf('.');
        return (i > 0) ? name.substring(i + 1) : "";
    }

    public static String getRemoteFileName(HttpURLConnection connection, URL url) {
        String disposition = connection.getHeaderField("Content-Disposition");

        if (disposition != null && disposition.contains("filename=")) {
            return disposition
                    .split("filename=")[1]
                    .replace("\"", "")
                    .trim();
        }

        String path = url.getPath();
        String name = path.substring(path.lastIndexOf('/') + 1);

        if (!name.contains(".")) {
            String contentType = connection.getContentType();
            if (contentType != null) {
                if (contentType.contains("csv")) {
                    return name + ".csv";
                }
                if (contentType.contains("txt")) {
                    return name + ".txt";
                }
                if (contentType.contains("parquet")) {
                    return name + ".parquet";
                }
                if (contentType.contains("zip")) {
                    return name + ".zip";
                }
            }
        }

        return name.isEmpty() ? "downloaded_file" : name;
    }

}