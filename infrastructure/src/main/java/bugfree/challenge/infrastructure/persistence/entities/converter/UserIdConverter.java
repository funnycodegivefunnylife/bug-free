package bugfree.challenge.infrastructure.persistence.entities.converter;

import bugfree.challenge.domain.entities.idclasses.UserId;
import jakarta.persistence.AttributeConverter;

public class UserIdConverter implements AttributeConverter<UserId, String> {


    @Override
    public String convertToDatabaseColumn(UserId userId) {
        if (userId == null) {
            return null; // Handle null case
        }
        return userId.value(); // Convert UserId to its String representation
    }

    @Override
    public UserId convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return null; // Handle null or empty case
        }
        return new UserId(s); // Convert String back to UserId
    }

}
