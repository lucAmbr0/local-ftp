package com.ambroo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.ArrayList;
import java.util.List;

public class UILogAppender extends AppenderBase<ILoggingEvent> {

    public static LogListener listener;
    private static final List<String> buffer = new ArrayList<>();

    @Override 
    protected void append(ILoggingEvent event) {
        if (event.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.INFO)) {
            if (listener != null) {
                listener.onLog(event.getFormattedMessage());
            } else {
                buffer.add(event.getFormattedMessage());
            }
        }

        java.io.File logsDir = new java.io.File("Logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        java.util.Date now = new java.util.Date();
        java.text.SimpleDateFormat dateFormatForFile = new java.text.SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatForFile.format(now);
        java.text.SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateTimeString = dateTimeFormat.format(now);

        int logNumber = 1;
        java.io.File logFile;
        while (true) {
            logFile = new java.io.File(logsDir, String.format("log_%s_%d.txt", dateString, logNumber));
            if (!logFile.exists() || logFile.length() <= 2048 * 1024) {
                break;
            }
            logNumber++;
        }

        String logLine = String.format("[%s] [%s] [%s] %s", 
            dateTimeString, 
            event.getLevel().toString(), 
            event.getLoggerName(), 
            event.getFormattedMessage()
        );

        try (java.io.FileWriter fw = new java.io.FileWriter(logFile, true)) {
            fw.write(logLine + System.lineSeparator());
        } catch (java.io.IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }

    public interface LogListener {
        void onLog(String message);
    }

    public static void setLogListener(LogListener logListener) {
        listener = logListener;
        // Flush buffered logs
        for (String msg : buffer) {
            listener.onLog(msg);
        }
        buffer.clear();
    }
}
