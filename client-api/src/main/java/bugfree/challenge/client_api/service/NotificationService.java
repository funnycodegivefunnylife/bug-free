package bugfree.challenge.client_api.service;

import bugfree.challenge.shared.kafka.KafkaRequestIdUtils;
import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for sending notification events to Kafka
 */
@Service
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final String NOTIFICATION_TOPIC = "notification-events";
    
    private final KafkaTemplate<String, String> kafkaTemplate;
    
    public NotificationService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public void sendNotification(String userId, String message) {
        String requestId = RequestIdMDC.getRequestId();
        logger.info("Sending notification for user: {}", userId);
        
        try {
            // Create producer record
            ProducerRecord<String, String> record = new ProducerRecord<>(
                NOTIFICATION_TOPIC, 
                userId, 
                message
            );
            
            // Add request ID to headers
            KafkaRequestIdUtils.addRequestIdHeader(record);
            
            // Send message
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
            
            future.whenComplete((result, exception) -> {
                // Maintain request ID in callback
                RequestIdMDC.withRequestId(requestId, () -> {
                    if (exception == null) {
                        logger.info("Notification sent successfully to topic: {}, partition: {}, offset: {}",
                                   result.getRecordMetadata().topic(),
                                   result.getRecordMetadata().partition(),
                                   result.getRecordMetadata().offset());
                    } else {
                        logger.error("Failed to send notification", exception);
                    }
                });
            });
            
        } catch (Exception e) {
            logger.error("Error sending notification", e);
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
