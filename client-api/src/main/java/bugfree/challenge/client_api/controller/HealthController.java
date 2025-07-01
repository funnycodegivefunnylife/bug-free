package bugfree.challenge.client_api.controller;

import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {
    
    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        logger.info("Health check requested");
        
        return Map.of(
            "status", "UP",
            "requestId", RequestIdMDC.getRequestId(),
            "timestamp", System.currentTimeMillis()
        );
    }
}
