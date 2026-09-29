package internship.utils;

import org.slf4j.MDC;
import java.util.UUID;

public final class TraceIdManager {

    private static final String TRACE_ID_KEY = "traceId";

    private TraceIdManager() {}

    public static String generateAndSet() {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }

    public static String get() {
        return MDC.get(TRACE_ID_KEY);
    }

    public static void clear() {
        MDC.remove(TRACE_ID_KEY);
    }
}