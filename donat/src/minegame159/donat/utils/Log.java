package minegame159.donat.utils;

public class Log {
    public enum Level {
        Fatal,
        Error,
        Warning,
        Info,
        Debug,
        Trace
    }

    public static final Logger MAIN = new Logger("MAIN");
}
