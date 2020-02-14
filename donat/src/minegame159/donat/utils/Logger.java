package minegame159.donat.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public Log.Level level = Log.Level.Debug;

    private StringBuilder sb = new StringBuilder();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private String name;

    /** Creates new logger. */
    public Logger(String name) {
        this.name = name;
    }

    private void begin(Log.Level level) {
        sb.setLength(0);
        sb.append(dateFormat.format(new Date())).append(" [").append(name).append("] ").append(level).append(": ");
    }

    private void end() {
        System.out.println(sb);
    }

    public void fatal(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Fatal.ordinal()) return;
        begin(Log.Level.Fatal);
        sb.append(String.format(msg, args));
        end();
    }

    public void error(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Error.ordinal()) return;
        begin(Log.Level.Error);
        sb.append(String.format(msg, args));
        end();
    }

    public void warning(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Warning.ordinal()) return;
        begin(Log.Level.Warning);
        sb.append(String.format(msg, args));
        end();
    }

    public void info(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Info.ordinal()) return;
        begin(Log.Level.Info);
        sb.append(String.format(msg, args));
        end();
    }

    public void debug(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Debug.ordinal()) return;
        begin(Log.Level.Debug);
        sb.append(String.format(msg, args));
        end();
    }

    public void trace(String msg, Object... args) {
        if (level.ordinal() < Log.Level.Trace.ordinal()) return;
        begin(Log.Level.Trace);
        sb.append(String.format(msg, args));
        end();
    }
}
