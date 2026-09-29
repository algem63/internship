package internship.utils;

import io.qameta.allure.Allure;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AllureLoggingListener implements TestExecutionListener {

    private static final Map<String, String> traceIdsByTest = new ConcurrentHashMap<>();

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        String traceId = TraceIdManager.generateAndSet();
        traceIdsByTest.put(testIdentifier.getUniqueId(), traceId);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier,
                                  TestExecutionResult testExecutionResult) {
        if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED) {
            String traceId = traceIdsByTest.remove(testIdentifier.getUniqueId());
            if (traceId != null) {
                Allure.addAttachment("TraceId", traceId);
            }
            String log = RequestLogger.getLog();
            if (!log.isEmpty()) {
                Allure.addAttachment("Request Log",
                        new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8)));
            }
        }
        RequestLogger.clear();
        TraceIdManager.clear();
    }
}