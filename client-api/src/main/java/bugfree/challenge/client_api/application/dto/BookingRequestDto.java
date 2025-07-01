package bugfree.challenge.client_api.application.dto;

import lombok.Data;

@Data
public class BookingRequestDto {
    private String roomId;
    private Long startTime;
    private Long endTime;
    private String description;
}
