package bugfree.challenge.notification.consumer;

import bugfree.challenge.shared.kafka.KafkaRequestIdUtils;
import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Sample Kafka consumer demonstrating MDC usage
 */
@Component
public class NotificationEventConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationEventConsumer.class);
    
    @KafkaListener(topics = "notification-events", groupId = "notification-service")
    public void handleNotificationEvent(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        try {
            // Extract and set request ID from Kafka headers
            String requestId = KafkaRequestIdUtils.extractAndSetRequestId(record);
            
            logger.info("Received notification event from topic: {}, partition: {}, offset: {}", 
                       record.topic(), record.partition(), record.offset());
            
            // Process the notification event
            processNotification(record.value());
            
            // Acknowledge the message
            acknowledgment.acknowledge();
            
            logger.info("Successfully processed notification event");
            
        } catch (Exception e) {
            logger.error("Error processing notification event", e);
            // In a real implementation, you might want to send to a dead letter queue
        } finally {
            // Clean up MDC after processing
            RequestIdMDC.clearAll();
        }
    }
    
    private void processNotification(String eventData) {
        logger.debug("Processing notification: {}", eventData);
        
        // Simulate async processing with MDC propagation
        RequestIdMDC.withRequestId(RequestIdMDC.getRequestId(), () -> {
            try {
                Thread.sleep(100); // Simulate processing time
                logger.info("Notification processed successfully");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Processing interrupted", e);
            }
        });
    }
}
