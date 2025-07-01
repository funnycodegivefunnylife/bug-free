package bugfree.challenge.shared.kafka;

import bugfree.challenge.shared.mdc.RequestIdMDC;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;

/**
 * Utility class for handling request ID in Kafka messages
 */
public class KafkaRequestIdUtils {
    
    /**
     * Add current request ID to Kafka producer record headers
     * @param record the producer record to add headers to
     * @param <K> key type
     * @param <V> value type
     * @return the record with request ID header added
     */
    public static <K, V> ProducerRecord<K, V> addRequestIdHeader(ProducerRecord<K, V> record) {
        String requestId = RequestIdMDC.getRequestId();
        if (StringUtils.hasText(requestId)) {
            record.headers().add(RequestIdMDC.REQUEST_ID_KEY, requestId.getBytes());
        }
        return record;
    }
    
    /**
     * Extract request ID from Kafka consumer record headers and set it in MDC
     * @param record the consumer record to extract request ID from
     * @param <K> key type
     * @param <V> value type
     * @return the extracted request ID or null if not found
     */
    public static <K, V> String extractAndSetRequestId(ConsumerRecord<K, V> record) {
        if (record.headers() != null) {
            var requestIdHeader = record.headers().lastHeader(RequestIdMDC.REQUEST_ID_KEY);
            if (requestIdHeader != null && requestIdHeader.value() != null) {
                String requestId = new String(requestIdHeader.value());
                RequestIdMDC.setRequestId(requestId);
                return requestId;
            }
        }
        // If no request ID found, generate a new one
        return RequestIdMDC.generateAndSetRequestId();
    }
    
    /**
     * Create a Spring message with request ID header
     * @param payload the message payload
     * @param <T> payload type
     * @return Spring message with request ID header
     */
    public static <T> Message<T> createMessageWithRequestId(T payload) {
        MessageBuilder<T> builder = MessageBuilder.withPayload(payload);
        
        String requestId = RequestIdMDC.getRequestId();
        if (StringUtils.hasText(requestId)) {
            builder.setHeader(RequestIdMDC.REQUEST_ID_KEY, requestId);
        }
        
        return builder.build();
    }
    
    /**
     * Extract request ID from Spring message headers and set it in MDC
     * @param message the Spring message
     * @param <T> payload type
     * @return the extracted request ID or null if not found
     */
    public static <T> String extractAndSetRequestId(Message<T> message) {
        Object requestIdHeader = message.getHeaders().get(RequestIdMDC.REQUEST_ID_KEY);
        if (requestIdHeader != null) {
            String requestId = requestIdHeader.toString();
            RequestIdMDC.setRequestId(requestId);
            return requestId;
        }
        // If no request ID found, generate a new one
        return RequestIdMDC.generateAndSetRequestId();
    }
}
