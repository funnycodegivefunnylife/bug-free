package bugfree.challenge.domain.kafka;

import bugfree.challenge.domain.kafka.data.KafkaEventData;

import java.util.Optional;

public interface BaseKafkaConsumer<T extends KafkaEventData> {


    BaseKafkaProcessor<T> getProcessor();

    /**
     * When the event is processed failure, this method will be called.
     * @param eventData the event data that failed to process
     */
    void fallback(T eventData);

    /**
     * This method will be called before the event is processed.
     * @param eventData the event data that will be processed
     */
    void afterProcess(T eventData);

    default Optional<T> parseMessage(String message) {
        return Optional.ofNullable(getProcessor().getEventData(message));
    }

    default void consume(String message) {
        try {
            Optional<T> eventDataOpt = parseMessage(message);
            if (eventDataOpt.isPresent()) {
                T eventData = eventDataOpt.get();
                boolean result = getProcessor().process(eventData);
                if (result) {
                    afterProcess(eventData);
                } else {
                    fallback(eventData);
                }

            } else {
            }
        } catch (Exception e) {
            // Handle processing failure
            System.err.println("Error processing message: " + message);
            fallback(null); // Pass null or handle accordingly
        }
    }
}
