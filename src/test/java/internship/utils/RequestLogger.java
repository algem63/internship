package internship.utils;

public final class RequestLogger {

    private static final ThreadLocal<StringBuilder> logBuffer =
            ThreadLocal.withInitial(StringBuilder::new);

    private RequestLogger() {}

    public static void log(String message) {
        logBuffer.get().append(message).append("\n");
    }

    public static String getLog() {
        return logBuffer.get().toString();
    }

    public static void clear() {
        logBuffer.remove();
    }
}