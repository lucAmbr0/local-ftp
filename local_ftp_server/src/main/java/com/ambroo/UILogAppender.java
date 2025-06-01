package com.ambroo;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        File logsDir = new File("Logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }

        Date now = new java.util.Date();
        SimpleDateFormat dateFormatForFile = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = dateFormatForFile.format(now);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dateTimeString = dateTimeFormat.format(now);

        int logNumber = 1;
        File logFile;
        while (true) {
            logFile = new File(logsDir, String.format("log_%s_%d.txt", dateString, logNumber));
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

        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(logLine + System.lineSeparator());
        } catch (IOException e) {
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
