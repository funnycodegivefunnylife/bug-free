package bugfree.challenge.domain.kafka;

import java.util.Optional;

import bugfree.challenge.domain.kafka.data.KafkaEventData;
import bugfree.challenge.shared.parser.JacksonParser;

public interface BaseKafkaConsumer<T extends KafkaEventData> {

  BaseKafkaProcessor<T> getProcessor();

  /**
   * When the event is processed failure, this method will be called.
   *
   * @param eventData the event data that failed to process
   */
  void fallback(T eventData);

  /**
   * This method will be called before the event is processed.
   *
   * @param eventData the event data that will be processed
   */
  void afterProcess(T eventData);

  Class<T> getEventDataClass();

  default Optional<T> parseMessage(String message) {
    try {
      T data = JacksonParser.fromJson(message, getEventDataClass());
      return Optional.ofNullable(data);
    } catch (Exception e) {
    }

    return Optional.empty();
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
