package bugfree.challenge.shared.async;

import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * Task decorator to propagate MDC context to async tasks
 */
public class MDCTaskDecorator implements TaskDecorator {
    
    @Override
    public Runnable decorate(Runnable runnable) {
        // Capture the current MDC context
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        
        return () -> {
            try {
                // Set the captured MDC context in the async thread
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                // Clear MDC context after task completion
                MDC.clear();
            }
        };
    }
}
