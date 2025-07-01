package bugfree.challenge.domain.entities.idclasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class Uuid {
    private final String value;

    @JsonCreator
    public Uuid(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty");
        }
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Uuid next() {
        return new Uuid(java.util.UUID.randomUUID().toString());
    }
}
