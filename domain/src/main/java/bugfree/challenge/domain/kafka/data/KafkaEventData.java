package bugfree.challenge.domain.kafka.data;

public abstract class KafkaEventData {
  private String eventId;
  private String eventType;
  private Long timestamp;
  private String source;

  public abstract String getKafkaKey();
}
