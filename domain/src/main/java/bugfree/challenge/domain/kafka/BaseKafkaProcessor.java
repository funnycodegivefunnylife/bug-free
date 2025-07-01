package bugfree.challenge.domain.kafka;

import bugfree.challenge.domain.kafka.data.KafkaEventData;

public interface BaseKafkaProcessor<T extends KafkaEventData> {

    boolean process(T eventData);
}
