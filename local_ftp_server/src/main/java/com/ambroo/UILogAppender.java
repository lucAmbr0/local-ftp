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
