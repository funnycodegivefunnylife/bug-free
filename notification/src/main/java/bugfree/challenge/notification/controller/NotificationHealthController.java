package bugfree.challenge.notification.controller;

import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Simple health controller for notification service
 * Only available if running as web application
 */
@RestController
@ConditionalOnWebApplication
public class NotificationHealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationHealthController.class);
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        logger.info("Health check requested for notification service");
        
        return Map.of(
            "service", "notification-service",
            "status", "UP",
            "requestId", RequestIdMDC.getRequestId(),
            "timestamp", System.currentTimeMillis()
        );
    }
}
