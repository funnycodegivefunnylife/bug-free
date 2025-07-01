package bugfree.challenge.domain.entities.idclasses;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Value object representing a User's unique identifier. */
public record UserId(String value) {

  @JsonCreator
  public UserId(String value) {
    this.value = Objects.requireNonNull(value, "UserId value cannot be null");
    if (value.isEmpty()) {
      throw new IllegalArgumentException("UserId value cannot be empty");
    }
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  public static UserId next() {
    return new UserId(java.util.UUID.randomUUID().toString());
  }
}
