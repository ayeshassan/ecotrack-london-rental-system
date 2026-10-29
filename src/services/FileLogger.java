package services;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    public enum LogLevel {
        INFO, WARN, ERROR
    }

    private static FileLogger instance;
    private String filename;

    private FileLogger(String filename) {
        this.filename = filename;
    }
                        //uses singleton pattern to make sure only one instance of the logger exists and that all parts of the program use the same log file.
    public static FileLogger getInstance(String filename) {
        if (instance == null) {
            instance = new FileLogger(filename);
        }
        return instance;
    }

    public void log(LogLevel level, String message) {
        try {
            FileWriter fw = new FileWriter(filename, true);
            PrintWriter pw = new PrintWriter(fw);

            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            pw.println("[" + timestamp + "] " + level + ": " + message);
            pw.close();

        } catch (IOException e) {
            System.out.println("Logger error: " + e.getMessage());
        }
    }
}